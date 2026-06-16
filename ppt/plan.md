# 捷運車站管理系統 - 簡報規劃

## 簡報目標
建立一個互動式 HTML 簡報，介紹捷運車站管理系統的功能、架構和實作細節。

## 技術選擇
- **框架**: 純 HTML + Tailwind CSS (CDN)
- **互動**: Vanilla JavaScript
- **風格**: 現代化、簡潔、專業
- **導航**: 鍵盤方向鍵 (←/→) 或點擊按鈕切換

## 簡報大綱

### 投影片 1: 封面
- 標題: 捷運車站管理系統
- 副標題: 乘客回饋功能實作
- 技術棧: Spring Boot + H2 + Bootstrap 5
- 作者資訊

### 投影片 2: 專案概述
- 專案背景
- 核心功能
  - 車站管理
  - 乘客回饋系統
- 技術亮點

### 投影片 3: 系統架構
- 前端架構
  - HTML/CSS/JavaScript
  - Bootstrap 5 UI
  - Fetch API
- 後端架構
  - Spring Boot 3.2.0
  - Spring Data JPA
  - H2 Database

### 投影片 4: 資料庫設計
- Station 實體
  - id, code, name, line, createdAt
- Feedback 實體
  - id, station, rating, category, comment, status, createdAt, updatedAt
- 關聯關係: @ManyToOne

### 投影片 5: 後端 API 設計
- RESTful API 端點
  - POST /api/feedbacks - 建立回饋
  - GET /api/feedbacks - 取得所有回饋
  - GET /api/feedbacks/{id} - 取得單一回饋
  - PATCH /api/feedbacks/{id}/status - 更新狀態
  - DELETE /api/feedbacks/{id} - 刪除回饋
  - GET /api/feedbacks/statistics/* - 統計資訊

### 投影片 6: 核心功能 - 回饋管理
- 功能特色
  - 評分系統 (1-5 星)
  - 分類管理 (清潔/服務/設施/安全/其他)
  - 狀態追蹤 (待處理/處理中/已完成)
  - 即時統計
- 截圖展示

### 投影片 7: 前端實作亮點
- 響應式設計
- 即時資料更新
- 表單驗證
- 錯誤處理
- 使用者體驗優化

### 投影片 8: 技術挑戰與解決方案
- 挑戰 1: Hibernate Lazy Loading 序列化問題
  - 解決: @JsonIgnoreProperties
- 挑戰 2: 資料驗證
  - 解決: Bean Validation + Service Layer
- 挑戰 3: 統計計算
  - 解決: Stream API + DTO

### 投影片 9: 程式碼品質
- 分層架構
  - Controller → Service → Repository
- 錯誤處理機制
- 程式碼註解與文件
- AGENTS.md 開發指南

### 投影片 10: 測試與部署
- 測試工具
  - test-api.sh 腳本
  - H2 Console
  - 手動測試
- 部署方式
  - Maven 打包
  - Spring Boot 內建 Tomcat

### 投影片 11: 功能展示
- 系統截圖
  - 統計儀表板
  - 回饋表單
  - 回饋列表
  - 狀態管理

### 投影片 12: 未來規劃
- 功能擴展
  - 使用者認證
  - 回饋回覆功能
  - 資料匯出
  - 圖表視覺化
- 技術優化
  - 快取機制
  - 分頁功能
  - 全文搜尋

### 投影片 13: 學習成果
- 技術能力提升
  - Spring Boot 開發
  - RESTful API 設計
  - 前後端整合
  - 問題解決能力
- 專案管理
  - 需求分析
  - 架構設計
  - 文件撰寫

### 投影片 14: 結語
- 專案總結
- 感謝
- Q&A

## 設計規範

### 色彩方案
- 主色: #3B82F6 (藍色 - 捷運主題)
- 輔色: #10B981 (綠色 - 成功)
- 強調: #F59E0B (橘色 - 警告)
- 背景: #F9FAFB (淺灰)
- 文字: #1F2937 (深灰)

### 排版原則
- 標題: 大而清晰
- 內容: 簡潔有力
- 程式碼: 語法高亮
- 圖片: 適當大小
- 動畫: 流暢自然

### 互動功能
- 鍵盤導航: ← → 鍵切換
- 滑鼠導航: 點擊左右按鈕
- 進度指示: 顯示當前頁碼
- 全螢幕模式: F11 或按鈕

## 檔案結構
```
ppt/
├── plan.md (本檔案)
├── index.html (主簡報檔案)
└── README.md (使用說明)
```

## 實作步驟
1. ✅ 建立 plan.md 規劃文件
2. ⏳ 建立 index.html 主檔案
3. ⏳ 實作投影片內容
4. ⏳ 加入導航功能
5. ⏳ 優化樣式與動畫
6. ⏳ 測試與調整
7. ⏳ 建立 README.md

## 預期成果
一個專業、美觀、互動性強的 HTML 簡報，能夠清楚展示捷運車站管理系統的各個面向。