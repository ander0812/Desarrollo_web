// Utility for theme toggling via DOM and localStorage
export function initTheme() {
  try {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  } catch (e) {
    // ignore
  }
}

export function toggleTheme() {
  try {
    const root = document.documentElement;
    const isDark = root.classList.toggle('dark');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
    return isDark ? 'dark' : 'light';
  } catch (e) {
    return null;
  }
}

export function getTheme() {
  try {
    return localStorage.getItem('theme') === 'dark' ? 'dark' : 'light';
  } catch (e) {
    return 'light';
  }
}
