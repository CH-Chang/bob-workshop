// API 基礎 URL
const API_URL = '/api/stations';
const FEEDBACK_API_URL = '/api/feedbacks';

// 頁面載入時執行
document.addEventListener('DOMContentLoaded', function() {
    loadStations();
    loadStationOptions();
    loadFeedbacks();
    loadGlobalStatistics();
    setupFormSubmit();
    setupFeedbackFormSubmit();
});

/**
 * 載入所有車站
 */
async function loadStations() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error('載入車站失敗');
        }
        const stations = await response.json();
        displayStations(stations);
    } catch (error) {
        console.error('載入車站錯誤:', error);
        showError('載入車站資料失敗，請重新整理頁面');
    }
}

/**
 * 顯示車站列表
 */
function displayStations(stations) {
    const tbody = document.getElementById('stationList');
    
    if (stations.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="5" class="text-center text-muted">
                    <i class="bi bi-inbox"></i> 目前沒有車站資料
                </td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = stations.map(station => `
        <tr>
            <td><strong>${escapeHtml(station.code)}</strong></td>
            <td>${escapeHtml(station.name)}</td>
            <td>
                <span class="badge ${station.line === '紅線' ? 'bg-danger' : 'bg-warning text-dark'}">
                    ${escapeHtml(station.line)}
                </span>
            </td>
            <td>${formatDateTime(station.createdAt)}</td>
            <td class="text-center">
                <button class="btn btn-sm btn-outline-danger" onclick="deleteStation(${station.id}, '${escapeHtml(station.name)}')">
                    <i class="bi bi-trash"></i> 刪除
                </button>
            </td>
        </tr>
    `).join('');
}

/**
 * 設定表單提交事件
 */
function setupFormSubmit() {
    const form = document.getElementById('stationForm');
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const station = {
            code: document.getElementById('code').value.trim(),
            name: document.getElementById('name').value.trim(),
            line: document.getElementById('line').value
        };
        
        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(station)
            });
            
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || '新增車站失敗');
            }
            
            // 成功後重置表單並重新載入列表
            form.reset();
            showSuccess('車站新增成功！');
            loadStations();
        } catch (error) {
            console.error('新增車站錯誤:', error);
            showError(error.message || '新增車站失敗，請檢查輸入資料');
        }
    });
}

/**
 * 刪除車站
 */
async function deleteStation(id, name) {
    if (!confirm(`確定要刪除車站「${name}」嗎？`)) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || '刪除車站失敗');
        }
        
        showSuccess('車站刪除成功！');
        loadStations();
    } catch (error) {
        console.error('刪除車站錯誤:', error);
        showError(error.message || '刪除車站失敗');
    }
}

/**
 * 格式化日期時間
 */
function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toLocaleString('zh-TW', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
}

/**
 * 跳脫 HTML 特殊字元
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

/**
 * 顯示成功訊息
 */
function showSuccess(message) {
    showAlert(message, 'success');
}

/**
 * 顯示錯誤訊息
 */
function showError(message) {
    showAlert(message, 'danger');
}

/**
 * 顯示提示訊息
 */
function showAlert(message, type) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 start-50 translate-middle-x mt-3`;
    alertDiv.style.zIndex = '9999';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(alertDiv);
    
    // 3 秒後自動移除
    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}

// Made with Bob

// ==================== 回饋功能 ====================

/**
 * 載入車站選項到回饋表單
 */
async function loadStationOptions() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error('載入車站失敗');
        }
        const stations = await response.json();
        const select = document.getElementById('feedbackStation');
        
        // 清空現有選項（保留第一個預設選項）
        select.innerHTML = '<option value="">請選擇車站</option>';
        
        // 加入車站選項
        stations.forEach(station => {
            const option = document.createElement('option');
            option.value = station.id;
            option.textContent = `${station.code} - ${station.name}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('載入車站選項錯誤:', error);
    }
}

/**
 * 設定回饋表單提交事件
 */
function setupFeedbackFormSubmit() {
    const form = document.getElementById('feedbackForm');
    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const feedback = {
            station: {
                id: parseInt(document.getElementById('feedbackStation').value)
            },
            rating: parseInt(document.getElementById('rating').value),
            category: document.getElementById('category').value,
            comment: document.getElementById('comment').value.trim(),
            contactEmail: document.getElementById('contactEmail').value.trim() || null
        };
        
        try {
            const response = await fetch(FEEDBACK_API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(feedback)
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || '提交回饋失敗');
            }
            
            // 成功後重置表單並重新載入列表
            form.reset();
            showSuccess('回饋提交成功！感謝您的意見。');
            loadFeedbacks();
            loadGlobalStatistics();
        } catch (error) {
            console.error('提交回饋錯誤:', error);
            showError(error.message || '提交回饋失敗，請檢查輸入資料');
        }
    });
}

/**
 * 載入回饋列表
 */
async function loadFeedbacks(status = null) {
    try {
        let url = FEEDBACK_API_URL;
        if (status && status !== 'all') {
            url += `?status=${encodeURIComponent(status)}`;
        }
        
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('載入回饋失敗');
        }
        const feedbacks = await response.json();
        displayFeedbacks(feedbacks);
    } catch (error) {
        console.error('載入回饋錯誤:', error);
        document.getElementById('feedbackList').innerHTML = `
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle"></i> 載入回饋資料失敗
            </div>
        `;
    }
}

/**
 * 顯示回饋列表
 */
function displayFeedbacks(feedbacks) {
    const container = document.getElementById('feedbackList');
    
    if (feedbacks.length === 0) {
        container.innerHTML = `
            <div class="text-center text-muted py-4">
                <i class="bi bi-inbox fs-1"></i>
                <p class="mt-2">目前沒有回饋</p>
            </div>
        `;
        return;
    }
    
    container.innerHTML = feedbacks.map(feedback => `
        <div class="card mb-3 feedback-card">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-9">
                        <div class="d-flex align-items-center mb-2">
                            <h6 class="mb-0 me-3">
                                <i class="bi bi-geo-alt-fill text-primary"></i>
                                ${escapeHtml(feedback.station.name)} (${escapeHtml(feedback.station.code)})
                            </h6>
                            <span class="badge bg-primary me-2">${'⭐'.repeat(feedback.rating)}</span>
                            <span class="badge bg-secondary me-2">${escapeHtml(feedback.category)}</span>
                            <span class="badge bg-${getStatusColor(feedback.status)}">${escapeHtml(feedback.status)}</span>
                        </div>
                        <p class="mb-2">${escapeHtml(feedback.comment)}</p>
                        <small class="text-muted">
                            <i class="bi bi-clock"></i> ${formatDateTime(feedback.createdAt)}
                            ${feedback.contactEmail ? `<i class="bi bi-envelope ms-3"></i> ${escapeHtml(feedback.contactEmail)}` : ''}
                        </small>
                    </div>
                    <div class="col-md-3 text-end">
                        <select class="form-select form-select-sm mb-2" 
                                onchange="updateFeedbackStatus(${feedback.id}, this.value)">
                            <option value="待處理" ${feedback.status === '待處理' ? 'selected' : ''}>待處理</option>
                            <option value="處理中" ${feedback.status === '處理中' ? 'selected' : ''}>處理中</option>
                            <option value="已完成" ${feedback.status === '已完成' ? 'selected' : ''}>已完成</option>
                        </select>
                        <button class="btn btn-sm btn-outline-danger w-100" onclick="deleteFeedback(${feedback.id})">
                            <i class="bi bi-trash"></i> 刪除
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `).join('');
}

/**
 * 篩選回饋
 */
function filterFeedbacks(status) {
    // 更新按鈕狀態
    document.querySelectorAll('.btn-group button').forEach(btn => {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');
    
    // 載入篩選後的回饋
    loadFeedbacks(status === 'all' ? null : status);
}

/**
 * 更新回饋狀態
 */
async function updateFeedbackStatus(id, status) {
    try {
        const response = await fetch(`${FEEDBACK_API_URL}/${id}/status`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status })
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || '更新狀態失敗');
        }
        
        showSuccess('狀態更新成功');
        loadFeedbacks();
        loadGlobalStatistics();
    } catch (error) {
        console.error('更新狀態錯誤:', error);
        showError(error.message || '更新狀態失敗');
        loadFeedbacks(); // 重新載入以恢復原狀態
    }
}

/**
 * 刪除回饋
 */
async function deleteFeedback(id) {
    if (!confirm('確定要刪除此回饋嗎？')) {
        return;
    }
    
    try {
        const response = await fetch(`${FEEDBACK_API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || '刪除回饋失敗');
        }
        
        showSuccess('回饋已刪除');
        loadFeedbacks();
        loadGlobalStatistics();
    } catch (error) {
        console.error('刪除回饋錯誤:', error);
        showError(error.message || '刪除回饋失敗');
    }
}

/**
 * 載入全域統計資訊
 */
async function loadGlobalStatistics() {
    try {
        const response = await fetch(`${FEEDBACK_API_URL}/statistics/global`);
        if (!response.ok) {
            throw new Error('載入統計失敗');
        }
        const stats = await response.json();
        displayStatistics(stats);
    } catch (error) {
        console.error('載入統計錯誤:', error);
    }
}

/**
 * 顯示統計資訊
 */
function displayStatistics(stats) {
    document.getElementById('totalFeedbacks').textContent = stats.totalFeedbacks || 0;
    document.getElementById('averageRating').textContent = (stats.averageRating || 0).toFixed(1);
    document.getElementById('pendingCount').textContent = stats.pendingCount || 0;
    document.getElementById('completedCount').textContent = stats.completedCount || 0;
}

/**
 * 取得狀態對應的顏色
 */
function getStatusColor(status) {
    const colors = {
        '待處理': 'warning',
        '處理中': 'info',
        '已完成': 'success'
    };
    return colors[status] || 'secondary';
}
