# IBM Bob Workshop - 實戰演練專案

這是 IBM Bob Workshop 的實戰演練專案集合，包含多個實際應用場景的 Sample Applications，讓學員透過實作學習如何使用 IBM Bob 進行 AI 輔助開發。

## 🎯 Workshop 目標

本 Workshop 旨在幫助開發者：
- 🤖 **掌握 AI 輔助開發**：學習如何有效使用 IBM Bob 提升開發效率
- 💡 **實戰經驗累積**：透過真實場景的 Sample Application 進行實作
- 📚 **最佳實踐學習**：了解 Bob Rules、AGENTS.md、SKILL.md 等進階功能
- 🔧 **工具整合應用**：學習 Slash Commands、Memory Import 等實用技巧
- 🚀 **快速開發能力**：從現有程式碼基礎上快速新增功能

## 📁 專案結構

```
bob-workshop/
├── index.html                      # Workshop 行前準備網頁
├── styles.css                      # 樣式表
├── script.js                       # JavaScript 功能
├── assets/                         # 共用資源
│   └── workshop-theme.css          # Workshop 主題樣式
├── topics/                         # 實戰主題
│   ├── transaction-monitor/        # 💳 信用卡交易監控系統（開發者）
│   ├── station-system/             # 🚇 捷運站務管理系統（開發者）
│   ├── journey-analysis/           # 🗺️ 旅程分析系統（開發者）
│   └── business-innovation/        # 🎯 數位轉型創新提案（非技術）
└── README.md                       # 本文件
```

## 💳 實戰主題：信用卡交易監控系統

**難度**：⭐⭐⭐ 中等  
**時長**：2 小時  

### 專案簡介
為現有的信用卡交易系統新增即時交易監控功能，識別異常交易模式並提供風險預警。

### 學習重點
- 從現有程式碼快速新增功能
- 建立自訂 Slash Command
- 撰寫專案 Rules 確保程式碼品質
- 建立 AGENTS.md 指導 AI 助手
- 實作金融系統的安全最佳實踐

### 快速開始
```bash
cd topics/transaction-monitor
./mvnw spring-boot:run
```

詳細說明請參考：[transaction-monitor/README.md](topics/transaction-monitor/README.md)

## 🚇 實戰主題：捷運站務管理系統

**難度**：⭐⭐ 簡單  
**時長**：1.5 小時  


### 專案簡介
建立捷運站務管理系統，提供站點資訊查詢、路線規劃等功能。

### 快速開始
```bash
cd topics/station-system
./mvnw spring-boot:run
```

詳細說明請參考：[station-system/README.md](topics/station-system/README.md)

## 🗺️ 實戰主題：旅程分析系統

**難度**：⭐⭐⭐⭐ 進階  
**時長**：3 小時

### 專案簡介
分析使用者旅程資料，提供個人化建議和優化方案。

詳細說明請參考：[journey-analysis/journey-analysis.html](topics/journey-analysis/journey-analysis.html)

---

## 🎯 實戰主題：高雄捷運數位轉型創新提案

**難度**：⭐ 入門
**時長**：60 分鐘 + 5 分鐘 Pitch
**適合對象**：經理、副理、產品負責人、業務分析師（無需技術背景）

### 專案簡介
🎭 **角色扮演式 Workshop**！你們是高雄捷運的數位轉型專案小組，使用 IBM Bob 分析三個現有系統，提出創新的數位服務提案，並在 90 分鐘內完成 Pitch 簡報！

### 四大創新提案主題（4 選 1）
- 🎮 **捷運集點遊戲化** - 搭乘賺點數，完成任務解鎖徽章
- 🌟 **智慧通勤助理** - AI 個人化通勤建議服務
- 🎁 **捷運生活圈** - 整合周邊商家的生活服務平台
- 🏆 **綠色通勤獎勵** - 鼓勵環保通勤的永續計畫

### 團隊分工（4 人協作）
- **👔 Team Leader**（經理級）：策略方向、協調分工、最終 Pitch
- **💼 Business Analyst**（副理級）：系統分析、商業價值、ROI 評估
- **🎨 Innovation Designer**（產品負責人）：服務設計、使用者旅程
- **📊 Presentation Master**（簡報專家）：提案簡報製作（15-18 slides）

### 為什麼這個 Workshop 很特別？
- ✅ **活潑有趣**：角色扮演 + 創意發想 + 5 分鐘 Pitch 競賽
- ✅ **完全無需寫程式**：只需使用 Bob 提問和產生內容
- ✅ **真實商業情境**：基於實際系統的創新提案
- ✅ **快速產出**：60 分鐘完成專業級提案簡報
- ✅ **團隊協作**：4 人分工，發揮各自專長
- ✅ **精準時間控制**：5 分鐘 Pitch 訓練高效簡報技巧

詳細說明請參考：[business-innovation/README.md](topics/business-innovation/README.md)

## 🚀 如何使用本專案

### 1. Clone 專案
```bash
git clone https://github.com/owhc/bob-workshop.git
cd bob-workshop
```

### 2. 選擇實戰主題

#### 🎯 非技術人員（經理、副理、PM、BA）
- 選擇 `business-innovation`（創新提案 + 5 分鐘 Pitch）
- 活潑有趣的角色扮演式 Workshop
- 60 分鐘準備 + 5 分鐘正式 Pitch

#### 💻 開發者
- **初學者**：從 `station-system` 開始
- **中級開發者**：嘗試 `transaction-monitor`
- **進階挑戰**：挑戰 `journey-analysis`

### 3. 啟動 Sample Application
每個主題都包含完整的 Spring Boot 應用程式：
```bash
cd topics/[主題名稱]
./mvnw spring-boot:run
```

### 4. 使用 IBM Bob 開始開發
在 IBM Bob IDE 中開啟專案，參考各主題的說明文件開始實作。

## ✨ 功能特色

### 行前準備網頁
- 🎨 **IBM Bob 官網配色**：採用 IBM Bob 品牌色彩系統
- 📱 **完全響應式**：支援桌面、平板、手機等各種裝置
- 🔄 **雙分頁設計**：完整版和精簡版內容切換
- ♿ **無障礙支援**：符合 ARIA 標準，支援鍵盤導航

### Sample Applications
- 💻 **完整可運行**：每個主題都是完整的 Spring Boot 應用程式
- 📖 **詳細文件**：包含 README、API 文件、使用說明
- 🎯 **實戰導向**：基於真實業務場景設計
- 🔧 **漸進式學習**：從簡單到複雜，循序漸進

## 📄 授權

此專案為 IBM Bob Workshop 使用。

## 👤 聯絡資訊

**技術支援**：
- Owen Chen
- Email: owenchen@tw.ibm.com
- 電話: 0928-107-182

## 📝 更新日誌

### v1.1.0 (2026-05-13)
- ✨ 新增信用卡交易監控系統主題
- 📚 更新 README 說明 Workshop 目標與方向
- 🔗 新增 GitHub Repository 連結
- 📖 完善使用說明文件

### v1.0.0 (2026-05-05)
- ✨ 初始版本發布
- 🎨 深色科技風格設計
- 📱 完整響應式支援
- ♿ 無障礙功能實作
- 🔄 雙分頁內容切換

---

**文件版本**: 1.1  
**最後更新**: 2026-05-13  
**Repository**: https://github.com/owhc/bob-workshop.git