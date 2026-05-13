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
│   ├── transaction-monitor/        # 💳 信用卡交易監控系統
│   ├── station-system/             # 🚇 捷運站務管理系統
│   └── journey-analysis/           # 🗺️ 旅程分析系統
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
- 建立 SKILL.md 記錄技術知識
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

## 🚀 如何使用本專案

### 1. Clone 專案
```bash
git clone https://github.com/owhc/bob-workshop.git
cd bob-workshop
```

### 2. 選擇實戰主題
根據你的興趣和時間選擇適合的主題：
- 初學者：從 `station-system` 開始
- 中級開發者：嘗試 `transaction-monitor`
- 進階挑戰：挑戰 `journey-analysis`

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

## 🌐 部署到 GitHub Pages

### 方法 1：透過 GitHub 網頁介面

1. 在 GitHub 建立新的 repository
2. 上傳所有檔案
3. 進入 repository 的 Settings
4. 找到 "Pages" 選項
5. 在 "Source" 下選擇 "main" 分支
6. 點擊 "Save"
7. 等待幾分鐘後，網站就會發布在 `https://[你的使用者名稱].github.io/[repository名稱]/`

### 方法 2：透過 Git 指令

```bash
# 1. 初始化 Git repository
git init

# 2. 加入所有檔案
git add .

# 3. 提交變更
git commit -m "Initial commit: IBM Bob Workshop"

# 4. 連結到 GitHub repository
git remote add origin https://github.com/[你的使用者名稱]/[repository名稱].git

# 5. 推送到 GitHub
git branch -M main
git push -u origin main

# 6. 在 GitHub 設定中啟用 GitHub Pages
```

## 🎨 自訂設定

### 修改顏色主題

在 `styles.css` 的 `:root` 區塊中修改顏色變數：

```css
:root {
    /* IBM Bob 官網配色 */
    --primary-color: #0f62fe;      /* IBM Blue */
    --primary-hover: #0353e9;      /* IBM Blue Hover */
    --secondary-color: #8a3ffc;    /* Purple */
    --accent-color: #33b1ff;       /* Cyan */
    --background: #161616;         /* Carbon Gray 100 */
    --surface: #262626;            /* Carbon Gray 90 */
}
```

### 修改 Workshop 資訊

在 `index.html` 中搜尋並替換：
- `[請填入日期時間]` → 實際的 Workshop 日期時間
- `[請填入地點]` → 實際的 Workshop 地點

## 🔧 技術棧

### 前端
- HTML5 語義化標籤
- CSS3（變數、Flexbox、Grid、動畫）
- 原生 JavaScript（無需框架）

### 後端（Sample Applications）
- Spring Boot 3.2.0
- H2 記憶體資料庫
- JPA/Hibernate
- Maven

## 🌐 瀏覽器支援

- ✅ Chrome/Edge (最新版本)
- ✅ Firefox (最新版本)
- ✅ Safari (最新版本)
- ✅ 行動裝置瀏覽器

## 📱 響應式斷點

- **桌面**: > 768px
- **平板**: 481px - 768px
- **手機**: ≤ 480px

## 📄 授權

此專案為 IBM Bob Workshop 內部使用。

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