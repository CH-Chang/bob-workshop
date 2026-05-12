// API 基礎 URL
const API_BASE = '/api/transactions';

// 頁面載入時初始化
document.addEventListener('DOMContentLoaded', () => {
  loadStatistics();
  loadAllTransactions();
});

// 載入統計資料
async function loadStatistics() {
  try {
    const response = await fetch(`${API_BASE}/statistics`);
    const data = await response.json();
    
    document.getElementById('totalTransactions').textContent = data.totalTransactions;
    document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount);
    document.getElementById('approvedCount').textContent = data.approvedCount;
    document.getElementById('averageAmount').textContent = formatCurrency(data.averageAmount);
  } catch (error) {
    console.error('載入統計資料失敗:', error);
    showError('無法載入統計資料');
  }
}

// 載入所有交易
async function loadAllTransactions() {
  setActiveButton(0);
  try {
    const response = await fetch(API_BASE);
    const transactions = await response.json();
    displayTransactions(transactions);
  } catch (error) {
    console.error('載入交易失敗:', error);
    showError('無法載入交易資料');
  }
}

// 載入最近24小時交易
async function loadRecentTransactions() {
  setActiveButton(1);
  try {
    const response = await fetch(`${API_BASE}/recent?hours=24`);
    const transactions = await response.json();
    displayTransactions(transactions);
  } catch (error) {
    console.error('載入最近交易失敗:', error);
    showError('無法載入最近交易');
  }
}

// 載入高額交易
async function loadHighAmountTransactions() {
  setActiveButton(2);
  try {
    const response = await fetch(`${API_BASE}/high-amount?threshold=50000`);
    const transactions = await response.json();
    displayTransactions(transactions);
  } catch (error) {
    console.error('載入高額交易失敗:', error);
    showError('無法載入高額交易');
  }
}

// 顯示交易列表
function displayTransactions(transactions) {
  const tbody = document.getElementById('transactionsBody');
  
  if (transactions.length === 0) {
    tbody.innerHTML = '<tr><td colspan="7" class="loading">查無資料</td></tr>';
    return;
  }
  
  tbody.innerHTML = transactions.map(tx => `
    <tr>
      <td>${tx.id}</td>
      <td><code>${tx.card.maskedCardNumber}</code></td>
      <td>${tx.card.cardholderName}</td>
      <td>${tx.merchant.merchantName}</td>
      <td class="${tx.amount > 50000 ? 'amount amount-high' : 'amount'}">
        ${formatCurrency(tx.amount)}
      </td>
      <td>
        <span class="status-badge-table status-${tx.status}">
          ${getStatusText(tx.status)}
        </span>
      </td>
      <td>${formatDateTime(tx.transactionTime)}</td>
    </tr>
  `).join('');
}

// 設定按鈕啟用狀態
function setActiveButton(index) {
  const buttons = document.querySelectorAll('.filter-btn');
  buttons.forEach((btn, i) => {
    if (i === index) {
      btn.classList.add('active');
    } else {
      btn.classList.remove('active');
    }
  });
}

// 格式化金額
function formatCurrency(amount) {
  return new Intl.NumberFormat('zh-TW', {
    style: 'currency',
    currency: 'TWD',
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount);
}

// 格式化日期時間
function formatDateTime(dateTimeString) {
  const date = new Date(dateTimeString);
  return new Intl.DateTimeFormat('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  }).format(date);
}

// 取得狀態文字
function getStatusText(status) {
  const statusMap = {
    'APPROVED': '已核准',
    'PENDING': '處理中',
    'DECLINED': '已拒絕',
    'CANCELLED': '已取消',
    'REFUNDED': '已退款'
  };
  return statusMap[status] || status;
}

// 顯示錯誤訊息
function showError(message) {
  const tbody = document.getElementById('transactionsBody');
  tbody.innerHTML = `
    <tr>
      <td colspan="7" style="color: var(--red); text-align: center;">
        ⚠️ ${message}
      </td>
    </tr>
  `;
}

// Made with Bob
