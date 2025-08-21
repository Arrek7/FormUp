(function() {
  function showToast(msg, type) {
    var toast = document.createElement('div');
    toast.className = 'toast ' + (type === 'error' ? 'toast-error' : 'toast-success');
    toast.textContent = msg;
    document.body.appendChild(toast);
    // trigger anim
    requestAnimationFrame(function(){ toast.classList.add('show'); });
    setTimeout(function(){
      toast.classList.remove('show');
      setTimeout(function(){ toast.remove(); }, 300);
    }, 3000);
  }
  document.addEventListener('DOMContentLoaded', function() {
    var el = document.getElementById('flash-data');
    if (!el) return;
    var err = el.getAttribute('data-pwderr');
    var ok = el.getAttribute('data-pwdok');
    if (err && err !== 'null') showToast(err, 'error');
    if (ok && ok !== 'null') showToast(ok, 'success');
  });
})();

