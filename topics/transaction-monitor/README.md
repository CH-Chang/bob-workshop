# 信用卡交易監控系統 - IBM Bob Workshop Sample

> **用途**：IBM Bob Workshop 實戰演練
> **難度**：⭐⭐⭐ 中等

## 📋 專案簡介

這是一個信用卡交易監控系統的 Sample Application，展示了基本的交易查詢功能。在 Workshop 中，學員將使用 **IBM Bob** 新增交易監控與警示功能。

## 🎯 Workshop 目標

完成本 Workshop 後，你將能夠：
- ✅ 使用 Bob 從現有程式碼快速新增功能
- ✅ 建立自訂 Slash Command 提升開發效率
- ✅ 撰寫專案 Rules 確保程式碼品質
- ✅ 建立 AGENTS.md 指導 AI 助手
- ✅ 建立 SKILL.md 記錄技術知識
- ✅ 實作金融系統的安全最佳實踐

## 🚀 快速開始

### 系統需求

- Java 17 或以上
- Maven 3.6 或以上
- IBM Bob IDE

### 啟動應用程式

```bash
# 使用 Maven Wrapper
./mvnw spring-boot:run

# 或使用已安裝的 Maven
mvn spring-boot:run
```

應用程式將在 `http://localhost:8080` 啟動。

### 存取介面

- **Web UI**: http://localhost:8080
- **H2 資料庫控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:payment_db`
  - Username: `sa`
  - Password: (留空)
- **API 端點**: http://localhost:8080/api/transactions

## 📁 專案結構

```
payment-transaction-monitor/
├── src/main/java/com/payment/
│   ├── TransactionMonitorApplication.java  # 主應用程式
│   ├── model/                              # 資料模型
│   │   ├── Card.java                       # ✅ 信用卡實體
│   │   ├── Merchant.java                   # ✅ 商店實體
│   │   ├── Transaction.java                # ✅ 交易實體
│   │   └── TransactionAlert.java           # ❌ 待實作
│   ├── repository/                         # 資料存取層
│   │   ├── CardRepository.java             # ✅ 已提供
│   │   ├── MerchantRepository.java         # ✅ 已提供
│   │   ├── TransactionRepository.java      # ✅ 已提供
│   │   └── AlertRepository.java            # ❌ 待實作
│   ├── service/                            # 業務邏輯層
│   │   ├── TransactionService.java         # ✅ 已提供
│   │   └── AlertDetectionService.java      # ❌ 待實作
│   └── controller/                         # API 控制層
│       ├── TransactionController.java      # ✅ 已提供
│       └── AlertController.java            # ❌ 待實作
├── src/main/resources/
│   ├── application.yml                     # ✅ 應用程式配置
│   ├── data.sql                            # ✅ 測試資料
│   └── static/                             # 前端資源
│       ├── index.html                      # ✅ Web UI
│       ├── styles.css                      # ✅ 樣式
│       └── app.js                          # ✅ JavaScript
└── .bob/                                   # ❌ 待建立
    ├── slash-commands/
    │   └── analyze-security.md             # ❌ 待建立
    ├── rules/
    │   ├── coding-standards.md             # ❌ 待建立
    │   └── security-rules.md               # ❌ 待建立
    ├── AGENTS.md                           # ❌ 待建立
    └── SKILL.md                            # ❌ 待建立
```

## 🎯 Workshop 任務

### 👥 團隊分工建議（3+1 模式）

本 Workshop 採用 **3 人開發 + 1 人簡報** 的協作模式，充分展示 Bob 在程式開發與文件製作的多元應用。

---

### **開發組（3 人）**

#### **👤 Person A：基礎建設（難度 ⭐⭐）**
**任務：TransactionAlert Entity + Repository + 基礎 API**

**具體工作：**
1. 建立 `TransactionAlert.java` Entity
   - 參考 `Transaction.java` 結構
   - 欄位：alertId, transactionId, alertType, severity, detectedAt, description
2. 建立 `AlertRepository.java` 介面
3. 建立 `AlertController.java` 基礎 CRUD API
   - GET `/api/alerts` - 查詢所有警示
   - GET `/api/alerts/{id}` - 查詢單筆警示
   - GET `/api/alerts/transaction/{transactionId}` - 查詢交易的警示

**Bob 使用重點：**
```
使用 Bob Ask Mode 分析 Transaction.java 結構
使用 Bob Code Mode 產生 Entity 與 Repository
```

**時間：25 分鐘**

---

#### **👤 Person B：核心邏輯（難度 ⭐⭐⭐⭐）**
**任務：AlertDetectionService - 3 種偵測規則**

**具體工作：**
1. 建立 `AlertDetectionService.java`
2. 實作 3 種偵測規則：
   - **高額交易**：單筆超過 50,000 元
   - **頻繁交易**：1 小時內超過 5 筆（需查詢 TransactionRepository）
   - **重複交易**：5 分鐘內相同金額相同商店（複雜查詢）
3. 整合 TransactionRepository 與 AlertRepository
4. 實作自動偵測邏輯（新交易觸發檢查）

**Bob 使用重點：**
```
使用 Bob 實作複雜查詢邏輯
使用 Bob 優化效能（避免 N+1 查詢）
使用 Bob 產生單元測試
```

**時間：30 分鐘**

---

#### **👤 Person C：整合與進階功能（難度 ⭐⭐⭐）**
**任務：前端整合 + Bob 進階功能**

**具體工作：**
1. 前端警示顯示功能
   - 在 `index.html` 加入警示列表區塊
   - 在 `app.js` 加入 API 呼叫邏輯
   - 在 `styles.css` 加入警示樣式（紅色/黃色/橙色）
2. 建立 Bob 進階功能：
   - **Slash Command**: `/analyze-security`
   - **Rules**: `coding-standards.md` + `security-rules.md`
   - **AGENTS.md**: AI 助手指引
   - **SKILL.md**: 金融系統開發最佳實踐

**Bob 使用重點：**
```
使用 Bob 產生前端程式碼
使用 Bob 建立 Slash Command
使用 Bob 撰寫 Rules 與 AGENTS.md
```

**時間：30 分鐘**

---

### **📊 簡報組（1 人）**

#### **👤 Person D：技術簡報製作**
**任務：使用 Bob + PPT SKILL 製作成果簡報**

**簡報大綱（8-10 slides）：**

1. **封面** - 信用卡交易監控系統開發成果
2. **專案背景** - 客戶需求與技術挑戰
3. **系統架構** - 現有系統 + 新增模組架構圖
4. **開發成果 - Entity 設計** - TransactionAlert 資料模型
5. **開發成果 - 偵測規則** - 3 種規則實作邏輯
6. **開發成果 - API 端點** - REST API 設計與 Swagger UI
7. **Bob 使用技巧** - Ask/Code Mode、Slash Commands、Rules
8. **安全最佳實踐** - 卡號遮罩、SQL Injection 防護
9. **Demo 展示** - 系統操作截圖與警示觸發範例
10. **總結與心得** - 開發效率提升與學習收穫

**Bob 使用方式：**
```
使用 PPT SKILL 製作技術簡報，主題：信用卡交易監控系統開發成果

要求：
- IBM 企業風格設計
- 包含程式碼範例與架構圖
- 加入 Demo 截圖
- 專業技術呈現
- 10 slides 以內
```

**時間分配：**
- 0-15分鐘：與開發組討論大綱
- 15-45分鐘：使用 Bob 產生簡報初稿
- 45-75分鐘：根據開發進度更新內容、加入截圖
- 75-90分鐘：最終潤飾與排練

---

### ⏱️ 整體時程規劃（90 分鐘）

#### **Phase 1: 基礎建設（0-25 分鐘）**
- Person A：完成 Entity + Repository + 基礎 API
- Person B：研究 TransactionRepository 查詢方法
- Person C：分析前端現有結構
- Person D：與團隊討論簡報大綱

#### **Phase 2: 核心開發（25-55 分鐘）**
- Person A：協助 Person B 整合測試
- Person B：實作 3 種偵測規則
- Person C：開發前端警示功能
- Person D：使用 Bob 產生簡報初稿

#### **Phase 3: 整合與進階（55-75 分鐘）**
- Person A/B/C：整合測試、Debug
- Person C：建立 Slash Commands、Rules、AGENTS.md
- Person D：更新簡報內容、加入截圖

#### **Phase 4: 收尾與準備（75-90 分鐘）**
- 全員：最終測試
- Person C：使用 `/analyze-security` 檢查安全性
- Person D：簡報最終潤飾
- 全員：準備 Demo 與報告

---

### 🎯 依賴關係

```
Person A (Entity + Repository)
    ↓
Person B (Service - 偵測規則) ← 依賴 Person A
    ↓
Person C (Controller + 前端) ← 依賴 Person A & B
    ↓
Person D (簡報) ← 依賴全員成果
```

---

### 💡 協作建議

**關鍵同步點：**
1. **25分鐘**：Person A 完成後，Person B/C 開始整合
2. **55分鐘**：開發組提供截圖給 Person D
3. **75分鐘**：全員 Review 簡報內容

**避免衝突：**
- Person A/B/C 各自負責不同檔案
- 使用 Git 分支開發（可選）
- 定期同步進度

**彈性調整：**
- 如果 Person B 提前完成，協助 Person C 前端開發
- 如果 Person A 提前完成，協助撰寫測試或文件

## 📊 現有功能

### API 端點

| 方法 | 端點 | 說明 |
|------|------|------|
| GET | `/api/transactions` | 查詢所有交易 |
| GET | `/api/transactions/{id}` | 查詢單筆交易 |
| GET | `/api/transactions/card/{cardId}` | 查詢指定卡片的交易 |
| GET | `/api/transactions/recent?hours=24` | 查詢最近的交易 |
| GET | `/api/transactions/high-amount?threshold=50000` | 查詢高額交易 |
| GET | `/api/transactions/statistics` | 取得交易統計 |

### 測試資料

系統已預載以下測試資料：
- 5 張信用卡
- 8 家特約商店
- 20 筆交易（包含異常交易範例）

## 🔧 技術棧

- **框架**: Spring Boot 3.2.0
- **資料庫**: H2 (記憶體資料庫)
- **ORM**: JPA/Hibernate
- **前端**: HTML + CSS + JavaScript
- **字型**: IBM Plex Sans & IBM Plex Mono

## 💡 開發提示

### 使用 Bob 的最佳實踐

1. **分析現有程式碼**
   ```
   請分析 Transaction.java 的結構，我需要建立類似的 TransactionAlert Entity
   ```

2. **產生程式碼**
   ```
   根據設計文件，幫我建立 AlertDetectionService，實作高額交易偵測規則
   ```

3. **安全檢查**
   ```
   /analyze-security src/main/java/com/payment/controller/AlertController.java
   ```

### 金融系統開發重點

- ✅ 使用 `BigDecimal` 處理金額
- ✅ 卡號必須遮罩顯示
- ✅ 使用 Prepared Statement 防止 SQL Injection
- ✅ 記錄審計日誌
- ✅ 適當的錯誤處理

## 📚 參考資源

- [Spring Boot 文件](https://spring.io/projects/spring-boot)
- [IBM Bob 官方文件](https://bob.ibm.com/docs)
- [Workshop 設計文件](../design-docs/Workshop_主題_聯邦網通專屬方案.md)

## 🆘 常見問題

### Q: 如何重新載入測試資料？

A: 重新啟動應用程式即可，H2 資料庫會自動重建。

### Q: 如何查看資料庫內容？

A: 存取 http://localhost:8080/h2-console

### Q: API 回傳 JSON 循環參考錯誤？

A: 在 Entity 的關聯欄位上加上 `@JsonIgnore` 或使用 DTO。

## 📝 授權

本專案僅供 IBM Bob Workshop 教學使用。

---

**準備好開始挑戰了嗎？啟動應用程式，開啟 Bob，讓我們開始吧！** 🚀