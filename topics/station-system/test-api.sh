#!/bin/bash

# 捷運車站管理系統 - API 測試腳本
# 此腳本用於測試回饋功能的所有 API 端點

echo "=========================================="
echo "捷運車站管理系統 - API 測試"
echo "=========================================="
echo ""

BASE_URL="http://localhost:8080"
API_URL="${BASE_URL}/api"

# 顏色定義
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 測試結果統計
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# 測試函數
test_api() {
    local test_name=$1
    local method=$2
    local endpoint=$3
    local data=$4
    
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    echo -e "${YELLOW}測試 ${TOTAL_TESTS}: ${test_name}${NC}"
    
    if [ -z "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X ${method} "${API_URL}${endpoint}")
    else
        response=$(curl -s -w "\n%{http_code}" -X ${method} \
            -H "Content-Type: application/json" \
            -d "${data}" \
            "${API_URL}${endpoint}")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
        echo -e "${GREEN}✓ 通過 (HTTP ${http_code})${NC}"
        PASSED_TESTS=$((PASSED_TESTS + 1))
    else
        echo -e "${RED}✗ 失敗 (HTTP ${http_code})${NC}"
        FAILED_TESTS=$((FAILED_TESTS + 1))
    fi
    
    echo "回應: ${body}" | head -c 200
    echo ""
    echo ""
}

echo "開始測試..."
echo ""

# 1. 測試車站 API
echo "=========================================="
echo "1. 車站 API 測試"
echo "=========================================="
echo ""

test_api "取得所有車站" "GET" "/stations"

# 2. 測試回饋 API
echo "=========================================="
echo "2. 回饋 API 測試"
echo "=========================================="
echo ""

test_api "建立回饋 - 評分 5" "POST" "/feedbacks" \
'{
  "station": {"id": 1},
  "rating": 5,
  "category": "清潔",
  "comment": "車站非常乾淨，地板光亮如新！",
  "contactEmail": "user1@example.com"
}'

test_api "建立回饋 - 評分 4" "POST" "/feedbacks" \
'{
  "station": {"id": 2},
  "rating": 4,
  "category": "服務",
  "comment": "服務人員態度親切，很有幫助。"
}'

test_api "建立回饋 - 評分 3" "POST" "/feedbacks" \
'{
  "station": {"id": 3},
  "rating": 3,
  "category": "設施",
  "comment": "設施還可以，但有些地方需要維修。"
}'

test_api "建立回饋 - 評分 2" "POST" "/feedbacks" \
'{
  "station": {"id": 1},
  "rating": 2,
  "category": "安全",
  "comment": "照明不足，晚上感覺不太安全。"
}'

test_api "建立回饋 - 評分 1" "POST" "/feedbacks" \
'{
  "station": {"id": 2},
  "rating": 1,
  "category": "其他",
  "comment": "等車時間太長，希望能改善。"
}'

test_api "取得所有回饋" "GET" "/feedbacks"

test_api "取得待處理回饋" "GET" "/feedbacks?status=待處理"

test_api "取得車站 1 的回饋" "GET" "/feedbacks/station/1"

test_api "取得全域統計" "GET" "/feedbacks/statistics/global"

test_api "取得車站 1 統計" "GET" "/feedbacks/statistics/station/1"

test_api "更新回饋狀態為處理中" "PATCH" "/feedbacks/1/status" \
'{
  "status": "處理中"
}'

test_api "更新回饋狀態為已完成" "PATCH" "/feedbacks/1/status" \
'{
  "status": "已完成"
}'

# 3. 測試錯誤處理
echo "=========================================="
echo "3. 錯誤處理測試"
echo "=========================================="
echo ""

test_api "建立回饋 - 無效評分" "POST" "/feedbacks" \
'{
  "station": {"id": 1},
  "rating": 6,
  "category": "清潔",
  "comment": "測試無效評分"
}'

test_api "建立回饋 - 無效類別" "POST" "/feedbacks" \
'{
  "station": {"id": 1},
  "rating": 5,
  "category": "無效類別",
  "comment": "測試無效類別"
}'

test_api "建立回饋 - 車站不存在" "POST" "/feedbacks" \
'{
  "station": {"id": 99999},
  "rating": 5,
  "category": "清潔",
  "comment": "測試不存在的車站"
}'

test_api "取得不存在的回饋" "GET" "/feedbacks/99999"

test_api "更新不存在的回饋狀態" "PATCH" "/feedbacks/99999/status" \
'{
  "status": "處理中"
}'

# 測試結果統計
echo "=========================================="
echo "測試結果統計"
echo "=========================================="
echo ""
echo "總測試數: ${TOTAL_TESTS}"
echo -e "${GREEN}通過: ${PASSED_TESTS}${NC}"
echo -e "${RED}失敗: ${FAILED_TESTS}${NC}"
echo ""

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "${GREEN}所有測試通過！ 🎉${NC}"
    exit 0
else
    echo -e "${RED}有 ${FAILED_TESTS} 個測試失敗 ❌${NC}"
    exit 1
fi

# Made with Bob