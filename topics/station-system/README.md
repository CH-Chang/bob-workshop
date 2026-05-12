# 🚇 高雄捷運車站管理系統

> **IBM Bob Workshop 初始專案**  
> Kaohsiung Rapid Transit Corporation - Station Management System

這是一個用於 IBM Bob Workshop 的初始專案，提供基本的車站管理功能。學員將使用 Bob 在此基礎上新增乘客回饋功能。

---

## 📋 專案資訊

- **客戶**: 高雄捷運公司 (KRTC)
- **目標**: 使用 Bob 為現有系統新增乘客回饋功能
- **難度**: ⭐ 初級
- **時長**: 2 小時

---

## 🎯 現有功能

### 後端 API
- ✅ 車站 CRUD 操作
- ✅ H2 記憶體資料庫
- ✅ RESTful API 設計
- ✅ 完整的錯誤處理

### 前端 UI
- ✅ 車站列表顯示
- ✅ 新增車站表單
- ✅ 刪除車站功能
- ✅ Bootstrap 5 美化介面
- ✅ 即時更新（無需重新整理）

---

## 🛠️ 技術棧

- **後端**: Spring Boot 3.2.0 + Java 17
- **資料庫**: H2 Database (記憶體模式)
- **前端**: HTML + CSS + JavaScript (Vanilla JS)
- **UI 框架**: Bootstrap 5
- **建置工具**: Maven

---

## 🚀 快速開始

### 前置需求
- Java 17 或以上
- Maven 3.6 或以上

### 啟動步驟
```bash
# 1. 進入專案目錄
cd krtc-station-system

# 2. 啟動應用程式
mvn spring-boot:run
```

應用程式將在 `http://localhost:8080` 啟動。

---

## 🌐 訪問應用程式

### Web UI
```
http://localhost:8080
```

### H2 Console
```
http://localhost:8080/h2-console
```

**連線資訊**:
- JDBC URL: `jdbc:h2:mem:krtc`
- Username: `sa`
- Password: (留空)

### API 端點

| 方法 | 端點 | 說明 |
|------|------|------|
| GET | `/api/stations` | 取得所有車站 |
| GET | `/api/stations/{id}` | 取得單一車站 |
| POST | `/api/stations` | 新增車站 |
| PUT | `/api/stations/{id}` | 更新車站 |
| DELETE | `/api/stations/{id}` | 刪除車站 |
| GET | `/api/stations/line/{line}` | 依路線查詢車站 |

---

## 📝 API 使用範例

### 取得所有車站
```bash
curl http://localhost:8080/api/stations
```

### 新增車站
```bash
curl -X POST http://localhost:8080/api/stations \
  -H "Content-Type: application/json" \
  -d '{
    "code": "R10",
    "name": "美麗島",
    "line": "紅線"
  }'
```

### 刪除車站
```bash
curl -X DELETE http://localhost:8080/api/stations/1
```

---

## 📂 專案結構

```
krtc-station-system/
├── src/
│   ├── main/
│   │   ├── java/com/krtc/
│   │   │   ├── StationApplication.java      # 主程式
│   │   │   ├── model/
│   │   │   │   └── Station.java             # 車站實體
│   │   │   ├── repository/
│   │   │   │   └── StationRepository.java   # 資料存取層
│   │   │   ├── service/
│   │   │   │   └── StationService.java      # 業務邏輯層
│   │   │   └── controller/
│   │   │       └── StationController.java   # REST API 控制器
│   │   └── resources/
│   │       ├── application.yml              # 應用配置
│   │       ├── data.sql                     # 初始資料
│   │       └── static/                      # 前端資源
│   │           ├── index.html               # 主頁面
│   │           ├── css/
│   │           │   └── style.css            # 自訂樣式
│   │           └── js/
│   │               └── app.js               # 前端邏輯
│   └── test/
│       └── java/com/krtc/
├── pom.xml                                  # Maven 配置
└── README.md                                # 專案說明
```

---

## 🎓 Workshop 任務

在此專案基礎上，學員將使用 Bob 完成以下任務：

### 階段一：專案分析（20 分鐘）
- 使用 Bob Ask Mode 分析專案結構
- 理解現有 API 設計
- 規劃 Feedback 功能整合

### 階段二：實作回饋功能（40 分鐘）
- 建立 Feedback 實體
- 實作 Repository、Service、Controller
- 與 Station 建立關聯

### 階段三：前端整合（30 分鐘）
- 在現有 UI 中加入回饋功能
- 實作回饋提交表單
- 顯示回饋列表與統計

### 階段四：測試與驗證（20 分鐘）
- 功能測試
- API 測試
- 單元測試（選用）

### 階段五：審查與部署（15 分鐘）
- Code Review
- Security Review（使用自訂模式）
- 部署驗證

### 階段六：Bob 功能體驗（15 分鐘）
- 建立 Slash Commands (`/deploy-check`)
- 建立 Custom Mode (Transit Security Expert)
- 建立 Rules 檔案
- 建立 AGENTS.md

---

## 🔧 開發指南

### 建置專案
```bash
mvn clean package
```

### 執行測試
```bash
mvn test
```

### 清理專案
```bash
mvn clean
```

---

## 📊 初始資料

系統預設包含以下車站資料：

| 代碼 | 名稱 | 路線 |
|------|------|------|
| R3 | 高雄車站 | 紅線 |
| R4 | 美麗島 | 紅線 |
| R5 | 中央公園 | 紅線 |
| R6 | 三多商圈 | 紅線 |
| O5 | 美麗島 | 橘線 |
| O7 | 文化中心 | 橘線 |
| O8 | 五塊厝 | 橘線 |
| O10 | 衛武營 | 橘線 |

**注意**: H2 使用記憶體模式，重啟應用程式後資料會重置。

---

## 🆘 常見問題

### Q1: 如何查看 H2 資料庫內容？
**A**: 訪問 `http://localhost:8080/h2-console`，使用以下連線資訊：
- JDBC URL: `jdbc:h2:mem:krtc`
- Username: `sa`
- Password: (留空)

### Q2: 如何重置資料？
**A**: 重啟應用程式，H2 會自動重新建立資料庫並載入初始資料。

### Q3: 為什麼使用 H2 而不是 PostgreSQL/MySQL？
**A**: H2 是記憶體資料庫，適合開發和測試：
- 不需安裝資料庫軟體
- 快速啟動
- 自動重置
- 專注學習 Bob，不被環境困擾

### Q4: 前端無法連接後端？
**A**: 確認：
1. 後端已成功啟動
2. 訪問 `http://localhost:8080/api/stations` 測試 API
3. 檢查瀏覽器 Console 是否有錯誤

---

## 📚 相關資源

- [Spring Boot 官方文件](https://spring.io/projects/spring-boot)
- [H2 Database 文件](https://www.h2database.com/)
- [Bootstrap 5 文件](https://getbootstrap.com/docs/5.3/)
- [IBM Bob 文件](https://bob.ibm.com/docs)

---

## 📄 授權

本專案為 IBM Bob Workshop 教學用途。

---

## 👥 聯絡資訊

如有問題，請聯絡 Workshop 講師。

---

**準備好開始了嗎？讓我們用 Bob 為高雄捷運打造回饋系統！** 🚇🚀