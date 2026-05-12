# 信用卡交易監控系統 - IBM Bob Workshop Sample

> **客戶**：聯邦網通科技股份有限公司  
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
  - JDBC URL: `jdbc:h2:mem:uitc_db`
  - Username: `sa`
  - Password: (留空)
- **API 端點**: http://localhost:8080/api/transactions

## 📁 專案結構

```
uitc-transaction-monitor/
├── src/main/java/com/uitc/
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

### 階段 1：新增 TransactionAlert 實體（25 分鐘）

使用 Bob 分析現有程式碼，新增交易警示功能：

- 建立 `TransactionAlert` Entity
- 建立 `AlertRepository` 介面
- 建立基本 CRUD API

**提示**：參考 `Transaction.java` 的結構

### 階段 2：實作異常偵測規則（30 分鐘）

實作 3 種偵測規則：

1. **高額交易**：單筆超過 50,000 元
2. **頻繁交易**：1 小時內超過 5 筆
3. **重複交易**：5 分鐘內相同金額相同商店

**提示**：使用 `TransactionRepository` 的查詢方法

### 階段 3：Bob 進階功能（30 分鐘）

建立以下 Bob 相關文件：

1. **Slash Command**: `/analyze-security`
   - 用途：讓 Bob 分析程式碼安全性問題
   - 檢查：卡號遮罩、SQL Injection、輸入驗證

2. **Rules**: 
   - `coding-standards.md`：編碼規範
   - `security-rules.md`：安全規範

3. **AGENTS.md**: AI 助手指引

4. **SKILL.md**: 技術知識庫
   - 主題：金融系統開發最佳實踐

### 階段 4：測試與審查（20 分鐘）

- 使用 Bob 產生測試
- Code Review
- 安全檢查

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
   /analyze-security src/main/java/com/uitc/controller/AlertController.java
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