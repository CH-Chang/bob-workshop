# 🚇 捷運站務管理系統 - 乘客回饋功能實作計劃

## 📊 專案概況
- **現有系統**：完整的車站 CRUD 管理系統
- **目標功能**：新增乘客回饋系統
- **技術棧**：Spring Boot 3.2 + H2 Database + Bootstrap 5
- **預估時間**：2 小時

---

## 📋 詳細實作計劃

### 階段一：專案分析與環境準備（15分鐘）

**目標**：了解現有架構，確認開發環境

**任務清單**：
1. **啟動現有系統**
   - 執行 `cd topics/station-system && ./mvnw spring-boot:run`
   - 確認系統在 http://localhost:8080 正常運作
   - 測試現有車站 CRUD 功能

2. **分析現有架構**
   - 檢視 `Station.java` 實體設計
   - 理解 `StationService.java` 業務邏輯層
   - 研究 `StationController.java` API 設計模式

3. **規劃 Feedback 功能**
   - 設計資料庫 Schema（Feedback 表結構）
   - 規劃 API 端點（RESTful 設計）
   - 設計前端 UI 整合點

**產出**：
- ✅ 系統正常運作
- ✅ 架構理解文件
- ✅ Feedback 功能設計草圖

---

### 階段二：後端 Feedback 功能實作（40分鐘）

**目標**：建立完整的後端 Feedback 功能

#### 2.1 建立 Feedback 實體（10分鐘）

**檔案**：`src/main/java/com/metro/model/Feedback.java`

**需求規格**：
```java
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;
    
    @Column(nullable = false)
    private Integer rating; // 評分 1-5
    
    @Column(nullable = false, length = 20)
    private String category; // 類別：清潔/服務/設施/安全/其他
    
    @Column(nullable = false, length = 1000)
    private String comment; // 意見內容
    
    @Column(length = 100)
    private String contactEmail; // 選填，聯絡信箱
    
    @Column(nullable = false, length = 20)
    private String status; // 狀態：待處理/處理中/已完成
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "待處理";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

**關聯設計**：
- Feedback 與 Station 為多對一關係
- 使用 `@ManyToOne` 建立關聯

#### 2.2 建立 Repository 層（5分鐘）

**檔案**：`src/main/java/com/metro/repository/FeedbackRepository.java`

**需要的查詢方法**：
```java
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStationId(Long stationId);
    List<Feedback> findByCategory(String category);
    List<Feedback> findByStatus(String status);
    Long countByStationId(Long stationId);
    List<Feedback> findByRatingGreaterThanEqual(Integer rating);
    List<Feedback> findByStationIdOrderByCreatedAtDesc(Long stationId);
}
```

#### 2.3 建立 DTO 類別（5分鐘）

**檔案**：`src/main/java/com/metro/dto/FeedbackStatisticsDto.java`

```java
public class FeedbackStatisticsDto {
    private Long stationId;
    private String stationName;
    private Long totalFeedbacks;
    private Double averageRating;
    private Long pendingCount;
    private Map<String, Long> categoryDistribution;
}
```

#### 2.4 建立 Service 層（15分鐘）

**檔案**：`src/main/java/com/metro/service/FeedbackService.java`

**業務邏輯**：
```java
@Service
@Transactional
public class FeedbackService {
    
    // 建立回饋（驗證車站存在、評分範圍）
    public Feedback createFeedback(Feedback feedback);
    
    // 取得所有回饋
    public List<Feedback> getAllFeedbacks();
    
    // 取得單一回饋
    public Feedback getFeedbackById(Long id);
    
    // 取得車站回饋
    public List<Feedback> getFeedbacksByStation(Long stationId);
    
    // 更新處理狀態
    public Feedback updateFeedbackStatus(Long id, String status);
    
    // 刪除回饋
    public void deleteFeedback(Long id);
    
    // 取得車站統計（平均評分、回饋數）
    public FeedbackStatisticsDto getStationStatistics(Long stationId);
}
```

**驗證規則**：
- 評分必須在 1-5 之間
- 車站 ID 必須存在
- 類別必須為預定義選項（清潔/服務/設施/安全/其他）
- 意見內容不可為空且長度限制 1000 字
- 狀態必須為預定義選項（待處理/處理中/已完成）

#### 2.5 建立 Controller 層（10分鐘）

**檔案**：`src/main/java/com/metro/controller/FeedbackController.java`

**API 端點設計**：
```java
@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbackController {
    
    // POST /api/feedbacks - 建立回饋
    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback);
    
    // GET /api/feedbacks - 取得所有回饋
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks();
    
    // GET /api/feedbacks/{id} - 取得單一回饋
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id);
    
    // GET /api/feedbacks/station/{id} - 取得車站回饋
    @GetMapping("/station/{id}")
    public ResponseEntity<List<Feedback>> getFeedbacksByStation(@PathVariable Long id);
    
    // PATCH /api/feedbacks/{id}/status - 更新狀態
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateFeedbackStatus(@PathVariable Long id, @RequestBody Map<String, String> body);
    
    // DELETE /api/feedbacks/{id} - 刪除回饋
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id);
    
    // GET /api/feedbacks/statistics/{stationId} - 取得統計
    @GetMapping("/statistics/{stationId}")
    public ResponseEntity<FeedbackStatisticsDto> getStationStatistics(@PathVariable Long stationId);
}
```

**回應格式**：
- 成功：200 OK + 資料
- 建立：201 Created + 資料
- 錯誤：400 Bad Request + 錯誤訊息
- 不存在：404 Not Found

---

### 階段三：前端 UI 整合（30分鐘）

**目標**：在現有 UI 中整合回饋功能

#### 3.1 修改 HTML 結構（10分鐘）

**檔案**：`src/main/resources/static/index.html`

**新增區塊**：

1. **統計資訊卡片**（在頁面頂部）
```html
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5>總回饋數</h5>
                <h2 id="totalFeedbacks">0</h2>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5>平均評分</h5>
                <h2 id="averageRating">0.0</h2>
            </div>
        </div>
    </div>
    <!-- 其他統計卡片 -->
</div>
```

2. **回饋提交表單**（在車站列表上方）
```html
<div class="card mb-4">
    <div class="card-header bg-info text-white">
        <h5>💬 提交回饋</h5>
    </div>
    <div class="card-body">
        <form id="feedbackForm">
            <div class="row">
                <div class="col-md-4">
                    <label>選擇車站</label>
                    <select id="feedbackStation" class="form-select" required>
                        <option value="">請選擇車站</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label>評分</label>
                    <select id="rating" class="form-select" required>
                        <option value="">請選擇評分</option>
                        <option value="5">⭐⭐⭐⭐⭐ 非常滿意</option>
                        <option value="4">⭐⭐⭐⭐ 滿意</option>
                        <option value="3">⭐⭐⭐ 普通</option>
                        <option value="2">⭐⭐ 不滿意</option>
                        <option value="1">⭐ 非常不滿意</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label>類別</label>
                    <select id="category" class="form-select" required>
                        <option value="">請選擇類別</option>
                        <option value="清潔">🧹 清潔</option>
                        <option value="服務">👥 服務</option>
                        <option value="設施">🏢 設施</option>
                        <option value="安全">🛡️ 安全</option>
                        <option value="其他">📝 其他</option>
                    </select>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-8">
                    <label>意見內容</label>
                    <textarea id="comment" class="form-control" rows="3" required></textarea>
                </div>
                <div class="col-md-4">
                    <label>聯絡信箱（選填）</label>
                    <input type="email" id="contactEmail" class="form-control">
                    <button type="submit" class="btn btn-info w-100 mt-2">提交回饋</button>
                </div>
            </div>
        </form>
    </div>
</div>
```

3. **回饋列表區塊**（在車站列表下方）
```html
<div class="card">
    <div class="card-header bg-warning">
        <h5>📋 回饋列表</h5>
        <div class="btn-group">
            <button class="btn btn-sm btn-outline-dark" onclick="filterFeedbacks('all')">全部</button>
            <button class="btn btn-sm btn-outline-dark" onclick="filterFeedbacks('待處理')">待處理</button>
            <button class="btn btn-sm btn-outline-dark" onclick="filterFeedbacks('處理中')">處理中</button>
            <button class="btn btn-sm btn-outline-dark" onclick="filterFeedbacks('已完成')">已完成</button>
        </div>
    </div>
    <div class="card-body">
        <div id="feedbackList"></div>
    </div>
</div>
```

#### 3.2 實作 JavaScript 邏輯（15分鐘）

**檔案**：`src/main/resources/static/js/app.js`

**新增功能**：
```javascript
// API 基礎 URL
const FEEDBACK_API = '/api/feedbacks';

// 載入車站選項到下拉選單
async function loadStationOptions() {
    const stations = await fetchStations();
    const select = document.getElementById('feedbackStation');
    stations.forEach(station => {
        const option = document.createElement('option');
        option.value = station.id;
        option.textContent = `${station.code} - ${station.name}`;
        select.appendChild(option);
    });
}

// 提交回饋
async function submitFeedback(event) {
    event.preventDefault();
    const feedback = {
        station: { id: document.getElementById('feedbackStation').value },
        rating: parseInt(document.getElementById('rating').value),
        category: document.getElementById('category').value,
        comment: document.getElementById('comment').value,
        contactEmail: document.getElementById('contactEmail').value || null
    };
    
    try {
        const response = await fetch(FEEDBACK_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(feedback)
        });
        
        if (response.ok) {
            showSuccessMessage('回饋提交成功！');
            document.getElementById('feedbackForm').reset();
            loadFeedbacks();
            loadStatistics();
        } else {
            const error = await response.text();
            showErrorMessage(error);
        }
    } catch (error) {
        showErrorMessage('提交失敗：' + error.message);
    }
}

// 載入回饋列表
async function loadFeedbacks(filter = 'all') {
    try {
        let url = FEEDBACK_API;
        if (filter !== 'all') {
            url += `?status=${filter}`;
        }
        
        const response = await fetch(url);
        const feedbacks = await response.json();
        renderFeedbackList(feedbacks);
    } catch (error) {
        console.error('載入回饋失敗:', error);
    }
}

// 渲染回饋列表
function renderFeedbackList(feedbacks) {
    const container = document.getElementById('feedbackList');
    
    if (feedbacks.length === 0) {
        container.innerHTML = '<p class="text-muted text-center">目前沒有回饋</p>';
        return;
    }
    
    container.innerHTML = feedbacks.map(feedback => `
        <div class="card mb-3">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h6>${feedback.station.name} (${feedback.station.code})</h6>
                        <p class="mb-1">
                            <span class="badge bg-primary">${'⭐'.repeat(feedback.rating)}</span>
                            <span class="badge bg-secondary">${feedback.category}</span>
                            <span class="badge bg-${getStatusColor(feedback.status)}">${feedback.status}</span>
                        </p>
                        <p class="mb-1">${feedback.comment}</p>
                        <small class="text-muted">${formatDateTime(feedback.createdAt)}</small>
                    </div>
                    <div>
                        <select class="form-select form-select-sm mb-2" 
                                onchange="updateStatus(${feedback.id}, this.value)">
                            <option value="待處理" ${feedback.status === '待處理' ? 'selected' : ''}>待處理</option>
                            <option value="處理中" ${feedback.status === '處理中' ? 'selected' : ''}>處理中</option>
                            <option value="已完成" ${feedback.status === '已完成' ? 'selected' : ''}>已完成</option>
                        </select>
                        <button class="btn btn-sm btn-danger" onclick="deleteFeedback(${feedback.id})">刪除</button>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

// 更新回饋狀態
async function updateStatus(id, status) {
    try {
        const response = await fetch(`${FEEDBACK_API}/${id}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status })
        });
        
        if (response.ok) {
            showSuccessMessage('狀態更新成功');
            loadFeedbacks();
            loadStatistics();
        }
    } catch (error) {
        showErrorMessage('更新失敗：' + error.message);
    }
}

// 刪除回饋
async function deleteFeedback(id) {
    if (!confirm('確定要刪除此回饋嗎？')) return;
    
    try {
        const response = await fetch(`${FEEDBACK_API}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showSuccessMessage('回饋已刪除');
            loadFeedbacks();
            loadStatistics();
        }
    } catch (error) {
        showErrorMessage('刪除失敗：' + error.message);
    }
}

// 載入統計資訊
async function loadStatistics() {
    // 實作統計資訊載入邏輯
}

// 輔助函數
function getStatusColor(status) {
    const colors = {
        '待處理': 'warning',
        '處理中': 'info',
        '已完成': 'success'
    };
    return colors[status] || 'secondary';
}

function formatDateTime(dateTime) {
    return new Date(dateTime).toLocaleString('zh-TW');
}

function showSuccessMessage(msg) {
    // 使用 Bootstrap Toast 或 Alert 顯示成功訊息
    alert(msg);
}

function showErrorMessage(msg) {
    // 使用 Bootstrap Toast 或 Alert 顯示錯誤訊息
    alert(msg);
}

// 頁面載入時初始化
document.addEventListener('DOMContentLoaded', function() {
    loadStations();
    loadStationOptions();
    loadFeedbacks();
    loadStatistics();
    
    document.getElementById('feedbackForm').addEventListener('submit', submitFeedback);
});
```

#### 3.3 美化樣式（5分鐘）

**檔案**：`src/main/resources/static/css/style.css`

**新增樣式**：
```css
/* 統計卡片 */
.stats-card {
    transition: transform 0.2s;
}

.stats-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

/* 回饋卡片 */
.feedback-card {
    border-left: 4px solid #007bff;
}

/* 評分星星 */
.rating-stars {
    color: #ffc107;
    font-size: 1.2em;
}

/* 狀態標籤 */
.status-badge {
    padding: 0.5em 1em;
    border-radius: 20px;
}

/* 回饋表單 */
#feedbackForm textarea {
    resize: vertical;
    min-height: 80px;
}

/* 響應式調整 */
@media (max-width: 768px) {
    .stats-card {
        margin-bottom: 1rem;
    }
}
```

---

### 階段四：測試與驗證（15分鐘）

**目標**：確保功能正常運作

#### 4.1 功能測試（10分鐘）

**測試項目**：

1. **回饋提交測試**
   - ✅ 提交有效回饋
   - ✅ 驗證必填欄位（車站、評分、類別、意見）
   - ✅ 驗證評分範圍（1-5）
   - ✅ 驗證車站存在性
   - ✅ 驗證意見長度限制

2. **回饋查詢測試**
   - ✅ 查詢所有回饋
   - ✅ 依車站篩選
   - ✅ 依狀態篩選
   - ✅ 查詢統計資訊

3. **狀態管理測試**
   - ✅ 更新處理狀態（待處理→處理中→已完成）
   - ✅ 刪除回饋

4. **UI 互動測試**
   - ✅ 表單驗證
   - ✅ 即時更新
   - ✅ 錯誤訊息顯示
   - ✅ 成功訊息顯示

#### 4.2 API 測試（5分鐘）

**使用 curl 測試**：

```bash
# 1. 建立回饋
curl -X POST http://localhost:8080/api/feedbacks \
  -H "Content-Type: application/json" \
  -d '{
    "station": {"id": 1},
    "rating": 5,
    "category": "清潔",
    "comment": "車站非常乾淨，地板光亮如新！"
  }'

# 2. 查詢所有回饋
curl http://localhost:8080/api/feedbacks

# 3. 查詢特定車站回饋
curl http://localhost:8080/api/feedbacks/station/1

# 4. 更新狀態
curl -X PATCH http://localhost:8080/api/feedbacks/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "處理中"}'

# 5. 查詢統計
curl http://localhost:8080/api/feedbacks/statistics/1

# 6. 刪除回饋
curl -X DELETE http://localhost:8080/api/feedbacks/1
```

**預期結果**：
- 所有 API 回應正確的 HTTP 狀態碼
- 資料格式符合預期
- 錯誤處理正確

---

### 階段五：Bob 進階功能體驗（15分鐘）

**目標**：學習 Bob 的進階功能

#### 5.1 建立自訂 Slash Command（5分鐘）

**檔案**：`.bob/commands/deploy-check.md`

```markdown
# Deploy Check Command

執行部署前檢查清單

## 檢查項目

### 1. 程式碼品質
- [ ] 所有測試通過
- [ ] 無 linting 錯誤
- [ ] 無編譯警告
- [ ] 程式碼已格式化

### 2. 文件
- [ ] API 文件已更新
- [ ] README 已更新
- [ ] CHANGELOG 已更新
- [ ] 註解完整

### 3. 資料庫
- [ ] 資料庫遷移腳本已準備
- [ ] 測試資料已準備
- [ ] 備份計畫已確認

### 4. 環境
- [ ] 環境變數已設定
- [ ] 設定檔已更新
- [ ] 依賴套件已更新

### 5. 安全
- [ ] 敏感資訊已移除
- [ ] API 權限已檢查
- [ ] 輸入驗證已實作

## 執行方式

在 Bob 中輸入 `/deploy-check` 即可執行此檢查清單
```

#### 5.2 建立自訂 Mode（5分鐘）

**檔案**：`.bob/modes/transit-security.md`

```markdown
# Transit Security Expert Mode

你是一位捷運系統安全審查專家，專注於交通運輸系統的資訊安全。

## 專業領域

### 1. 乘客資料隱私
- 個人資料保護法（PDPA）合規
- 最小化資料收集原則
- 資料加密與安全儲存
- 資料保留政策

### 2. API 安全性
- 認證與授權機制
- Rate Limiting
- CORS 設定
- API 版本控制

### 3. 常見漏洞防護
- SQL Injection 防護
- XSS 攻擊防護
- CSRF 防護
- 輸入驗證與清理

### 4. 敏感資訊處理
- 不記錄敏感資訊
- 錯誤訊息不洩漏系統資訊
- 安全的日誌記錄

## 審查重點

當審查程式碼時，請特別注意：
1. 是否有未加密的敏感資料
2. 是否有 SQL Injection 風險
3. 是否有適當的輸入驗證
4. 是否有適當的錯誤處理
5. 是否符合最小權限原則

## 回應格式

審查結果請以以下格式呈現：
- ✅ 通過項目
- ⚠️ 需要注意的項目
- ❌ 必須修正的項目
- 💡 改善建議
```

#### 5.3 測試 Bob 功能（5分鐘）

**測試項目**：

1. **測試 Slash Command**
   - 在 Bob 中輸入 `/deploy-check`
   - 確認檢查清單正確顯示
   - 逐項檢查並標記完成

2. **測試自訂 Mode**
   - 切換到 Transit Security Expert 模式
   - 請 Bob 審查 `FeedbackController.java`
   - 檢視安全性建議

3. **測試 Bob 程式碼生成**
   - 請 Bob 生成單元測試
   - 請 Bob 優化現有程式碼
   - 請 Bob 新增 JavaDoc 註解

---

### 階段六：程式碼審查與文件（15分鐘）

**目標**：完善專案文件與程式碼品質

#### 6.1 建立 AGENTS.md（5分鐘）

**檔案**：`topics/station-system/AGENTS.md`

```markdown
# Station System Agent Rules

此檔案提供 AI 助手在處理此專案時的指導原則。

## 專案結構

- **框架**: Spring Boot 3.2.0 + Java 17
- **資料庫**: H2 Database (記憶體模式)
- **前端**: HTML + CSS + JavaScript (Vanilla JS)
- **UI 框架**: Bootstrap 5
- **建置工具**: Maven

## 程式碼規範

### 後端規範

1. **Entity 類別**
   - 必須使用 `@Entity` 註解
   - 必須有 `@Id` 主鍵
   - 使用 `@PrePersist` 和 `@PreUpdate` 處理時間戳記
   - 所有欄位必須有適當的驗證註解

2. **Service 層**
   - 必須使用 `@Service` 註解
   - 必須使用 `@Transactional` 註解
   - 所有公開方法必須有 JavaDoc 註解
   - 必須有適當的錯誤處理

3. **Controller 層**
   - 必須使用 `@RestController` 註解
   - 必須使用 `@RequestMapping` 定義基礎路徑
   - 必須使用 `@CrossOrigin` 允許跨域請求
   - 必須使用 `ResponseEntity` 包裝回應
   - 必須有完整的錯誤處理

4. **Repository 層**
   - 繼承 `JpaRepository`
   - 使用 Spring Data JPA 查詢方法命名規則

### 前端規範

1. **HTML**
   - 使用 Bootstrap 5 樣式類別
   - 使用語意化標籤
   - 必須有適當的 ARIA 標籤

2. **JavaScript**
   - 使用 ES6+ 語法
   - 使用 async/await 處理非同步操作
   - 必須有錯誤處理
   - 使用 fetch API 進行 HTTP 請求

3. **CSS**
   - 優先使用 Bootstrap 內建樣式
   - 自訂樣式放在 `style.css`
   - 使用 BEM 命名規則

## 資料庫規則

- **資料庫名稱**: `metro`
- **模式**: 記憶體模式 (H2)
- **初始資料**: 在 `data.sql` 中定義
- **DDL 策略**: `create-drop` (每次啟動重建)

## API 設計原則

1. **RESTful 設計**
   - GET: 查詢資源
   - POST: 建立資源
   - PUT: 完整更新資源
   - PATCH: 部分更新資源
   - DELETE: 刪除資源

2. **回應格式**
   - 成功: 200 OK
   - 建立: 201 Created
   - 無內容: 204 No Content
   - 錯誤: 400 Bad Request
   - 不存在: 404 Not Found

3. **錯誤處理**
   - 使用繁體中文錯誤訊息
   - 提供有意義的錯誤描述
   - 不洩漏系統內部資訊

## 安全考量

1. **資料隱私**
   - 不儲存敏感個人資訊
   - Email 為選填欄位
   - 實作基本的輸入驗證

2. **輸入驗證**
   - 驗證所有使用者輸入
   - 使用 Bean Validation 註解
   - 在 Service 層進行業務邏輯驗證

3. **錯誤訊息**
   - 不在錯誤訊息中洩漏系統資訊
   - 使用通用的錯誤訊息

## 測試原則

1. **單元測試**
   - 使用 JUnit 5
   - 測試覆蓋率至少 80%
   - 測試所有業務邏輯

2. **整合測試**
   - 測試 API 端點
   - 測試資料庫操作
   - 使用 `@SpringBootTest`

## 文件要求

1. **JavaDoc**
   - 所有公開類別必須有類別註解
   - 所有公開方法必須有方法註解
   - 註解必須包含參數和回傳值說明

2. **README**
   - 保持 README 更新
   - 記錄所有 API 端點
   - 提供使用範例

3. **註解**
   - 使用繁體中文註解
   - 解釋複雜的業務邏輯
   - 標記 TODO 和 FIXME
```

#### 6.2 建立 Rules 檔案（5分鐘）

**檔案**：`.bob/rules/station-system.md`

```markdown
# Station System Development Rules

## 程式碼風格

1. **命名規則**
   - 類別名稱使用 PascalCase
   - 方法名稱使用 camelCase
   - 常數使用 UPPER_SNAKE_CASE
   - 變數名稱使用 camelCase

2. **註解規則**
   - 所有公開 API 必須有 JavaDoc
   - 複雜邏輯必須有行內註解
   - 使用繁體中文註解

3. **格式化**
   - 縮排使用 4 個空格
   - 大括號不換行（K&R 風格）
   - 每行最多 120 字元

## 開發流程

1. **分支策略**
   - main: 穩定版本
   - develop: 開發版本
   - feature/*: 功能分支

2. **提交訊息**
   - 使用繁體中文
   - 格式: `[類型] 簡短描述`
   - 類型: feat, fix, docs, style, refactor, test

3. **Code Review**
   - 所有程式碼必須經過審查
   - 至少一位審查者批准
   - 通過所有測試

## 技術限制

1. **不使用的技術**
   - 不使用 Lombok（保持程式碼明確）
   - 不使用複雜的 ORM 關聯（保持簡單）
   - 不使用前端框架（使用 Vanilla JS）

2. **必須使用的技術**
   - Spring Boot 3.2.0
   - Java 17
   - H2 Database
   - Bootstrap 5

## 效能考量

1. **資料庫查詢**
   - 避免 N+1 查詢問題
   - 使用適當的索引
   - 限制查詢結果數量

2. **API 回應**
   - 回應時間應小於 200ms
   - 使用分頁處理大量資料
   - 快取常用資料

## 安全規則

1. **輸入驗證**
   - 驗證所有使用者輸入
   - 使用白名單驗證
   - 清理 HTML 輸入

2. **錯誤處理**
   - 不洩漏系統資訊
   - 記錄所有錯誤
   - 提供友善的錯誤訊息

3. **資料保護**
   - 不記錄敏感資訊
   - 使用 HTTPS（生產環境）
   - 實作適當的存取控制
```

#### 6.3 更新 README（5分鐘）

**檔案**：`topics/station-system/README.md`

**新增章節**：

```markdown
## 🆕 新增功能：乘客回饋系統

### 功能概述
乘客回饋系統允許使用者對車站服務提供意見和評分，幫助改善服務品質。

### Feedback API 文件

#### 建立回饋
```bash
POST /api/feedbacks
Content-Type: application/json

{
  "station": {"id": 1},
  "rating": 5,
  "category": "清潔",
  "comment": "車站非常乾淨！",
  "contactEmail": "user@example.com"
}
```

#### 查詢所有回饋
```bash
GET /api/feedbacks
```

#### 查詢車站回饋
```bash
GET /api/feedbacks/station/{stationId}
```

#### 更新回饋狀態
```bash
PATCH /api/feedbacks/{id}/status
Content-Type: application/json

{
  "status": "處理中"
}
```

#### 刪除回饋
```bash
DELETE /api/feedbacks/{id}
```

#### 查詢車站統計
```bash
GET /api/feedbacks/statistics/{stationId}
```

### 資料模型

#### Feedback Entity
- `id`: 回饋 ID
- `station`: 關聯車站
- `rating`: 評分 (1-5)
- `category`: 類別（清潔/服務/設施/安全/其他）
- `comment`: 意見內容
- `contactEmail`: 聯絡信箱（選填）
- `status`: 處理狀態（待處理/處理中/已完成）
- `createdAt`: 建立時間
- `updatedAt`: 更新時間

### 使用範例

1. **提交回饋**
   - 選擇車站
   - 選擇評分（1-5 星）
   - 選擇類別
   - 填寫意見
   - 選填聯絡信箱
   - 點擊提交

2. **查看回饋**
   - 在回饋列表中查看所有回饋
   - 可依狀態篩選
   - 可依車站篩選

3. **管理回饋**
   - 更新處理狀態
   - 刪除不適當的回饋

### 測試指南

#### 單元測試
```bash
./mvnw test
```

#### API 測試
使用提供的 curl 命令測試各個端點

#### 前端測試
1. 開啟瀏覽器開發者工具
2. 測試表單驗證
3. 測試 API 呼叫
4. 檢查錯誤處理
```

---

## 🎯 成功標準

完成後應達成：
- ✅ 後端 Feedback CRUD API 完整運作
- ✅ 前端 UI 整合完成，可正常提交與查看回饋
- ✅ 統計功能正常顯示
- ✅ 所有測試通過
- ✅ 建立自訂 Slash Command 和 Mode
- ✅ 完成 AGENTS.md 和 Rules 文件
- ✅ 程式碼符合規範，有完整註解
- ✅ README 已更新

---

## 📊 時間分配總結

| 階段 | 時間 | 主要任務 |
|------|------|----------|
| 階段一 | 15分鐘 | 專案分析與環境準備 |
| 階段二 | 40分鐘 | 後端 Feedback 功能實作 |
| 階段三 | 30分鐘 | 前端 UI 整合 |
| 階段四 | 15分鐘 | 測試與驗證 |
| 階段五 | 15分鐘 | Bob 進階功能體驗 |
| 階段六 | 15分鐘 | 程式碼審查與文件 |
| **總計** | **130分鐘** | **約 2 小時** |

---

## 💡 開發建議

1. **使用 Bob Ask Mode**
   - 在不確定時先詢問 Bob
   - 請 Bob 解釋現有程式碼
   - 請 Bob 提供最佳實踐建議

2. **善用 Bob Code Mode**
   - 讓 Bob 協助產生程式碼
   - 請 Bob 重構現有程式碼
   - 請 Bob 新增單元測試

3. **逐步測試**
   - 每完成一個階段就測試
   - 確認功能正常後再繼續
   - 記錄遇到的問題

4. **保持程式碼整潔**
   - 遵循現有的程式碼風格
   - 使用有意義的變數名稱
   - 新增適當的註解

5. **記錄學習心得**
   - 記錄使用 Bob 的技巧
   - 記錄遇到的問題和解決方法
   - 分享給團隊成員

---

## 🚀 開始實作

準備好了嗎？讓我們開始使用 Bob 為捷運系統打造回饋功能！

**下一步**：
1. 確認環境已準備好
2. 啟動現有系統
3. 開始階段一：專案分析

祝你實作順利！🎉