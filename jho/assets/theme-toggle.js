// IBM Bob Workshop - 共用 JavaScript 功能

/**
 * 切換 Prompt 顯示/隱藏
 */
function togglePrompt(header) {
  const body = header.nextElementSibling;
  const toggle = header.querySelector('.prompt-toggle');
  body.classList.toggle('visible');
  toggle.classList.toggle('open');
}

/**
 * 複製 Prompt 文字到剪貼簿
 */
function copyPrompt(buttonElement) {
  // 找到最近的 prompt-content 容器
  const promptContent = buttonElement.closest('.prompt-content');
  const promptText = promptContent.querySelector('.prompt-text');
  
  if (promptText) {
    const text = promptText.textContent;
    navigator.clipboard.writeText(text).then(() => {
      // 暫時改變按鈕文字
      const originalText = buttonElement.textContent;
      buttonElement.textContent = '✓ 已複製！';
      buttonElement.style.background = '#50fa7b';
      
      setTimeout(() => {
        buttonElement.textContent = originalText;
        buttonElement.style.background = '';
      }, 2000);
    }).catch(err => {
      alert('複製失敗：' + err);
    });
  }
}

/**
 * 頁面載入完成後自動展開第一個 Prompt
 */
document.addEventListener('DOMContentLoaded', () => {
  const firstBody = document.querySelector('.prompt-body');
  const firstToggle = document.querySelector('.prompt-toggle');
  
  if (firstBody && firstToggle) {
    firstBody.classList.add('visible');
    firstToggle.classList.add('open');
  }
});

/**
 * 平滑捲動到指定元素
 */
function scrollToElement(elementId) {
  const element = document.getElementById(elementId);
  if (element) {
    element.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}

// Made with Bob
