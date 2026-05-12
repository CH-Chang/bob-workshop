// API 基礎 URL
const API_URL = '/api/stations';

// 頁面載入時執行
document.addEventListener('DOMContentLoaded', function() {
    loadStations();
    setupFormSubmit();
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
