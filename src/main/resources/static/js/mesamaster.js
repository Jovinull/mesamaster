/**
 * MesaMaster UI — Micro-interações e Feedback Visual
 */
(function () {
  'use strict';

  /* --------------------------------------------------------
     1. Efeito Ripple em Botões
     Cria uma onda animada no ponto exato do clique.
  -------------------------------------------------------- */
  function initRipple() {
    document.addEventListener('click', function (e) {
      const btn = e.target.closest('.btn');
      if (!btn) return;

      const ripple = document.createElement('span');
      ripple.classList.add('mm-ripple');

      const rect = btn.getBoundingClientRect();
      const size = Math.max(rect.width, rect.height);
      ripple.style.width  = ripple.style.height = size + 'px';
      ripple.style.left   = (e.clientX - rect.left - size / 2) + 'px';
      ripple.style.top    = (e.clientY - rect.top  - size / 2) + 'px';

      btn.appendChild(ripple);
      ripple.addEventListener('animationend', () => ripple.remove());
    });
  }

  /* --------------------------------------------------------
     2. Estado de Loading em Formulários
     Ao submeter um form, mostra spinner no botão submit
     para evitar sensação de travamento.
  -------------------------------------------------------- */
  function initFormLoading() {
    document.querySelectorAll('form').forEach(function (form) {
      // Ignora forms de confirmação de exclusão (já têm confirm dialog)
      if (form.hasAttribute('data-no-loading')) return;

      form.addEventListener('submit', function (e) {
        const submitBtn = form.querySelector('[type="submit"]:not([data-no-loading])');
        if (!submitBtn) return;

        // Aguarda confirmação do browser (caso haja onsubmit=confirm)
        // Se o evento foi cancelado, não mostra loading
        if (e.defaultPrevented) return;

        setTimeout(function () {
          if (!submitBtn.disabled) {
            submitBtn.classList.add('mm-loading');
          }
        }, 10);
      });
    });
  }

  /* --------------------------------------------------------
     3. Transição de Entrada da Página
     Anima o conteúdo principal ao carregar a página.
  -------------------------------------------------------- */
  function initPageTransition() {
    const main = document.querySelector('.container, .mm-login-wrapper');
    if (main) {
      main.classList.add('mm-page-enter');
    }
  }

  /* --------------------------------------------------------
     4. Active Link na Navbar
     Marca o link ativo com base na URL atual.
  -------------------------------------------------------- */
  function initActiveNavLink() {
    const path = window.location.pathname;
    document.querySelectorAll('.mm-navbar .nav-link').forEach(function (link) {
      const href = link.getAttribute('href');
      if (href && href !== '/' && path.startsWith(href)) {
        link.classList.add('active');
      }
    });
  }

  /* --------------------------------------------------------
     5. Confirmações Acessíveis
     Substitui confirm() nativo por um atributo data mais
     limpo quando disponível.
  -------------------------------------------------------- */
  function initConfirmForms() {
    document.querySelectorAll('[data-confirm]').forEach(function (el) {
      el.addEventListener('click', function (e) {
        const msg = el.getAttribute('data-confirm');
        if (!window.confirm(msg)) {
          e.preventDefault();
          e.stopPropagation();
        }
      });
    });
  }

  /* --------------------------------------------------------
     6. Auto-dismiss de Alertas
     Remove alertas de sucesso automaticamente após 5s.
  -------------------------------------------------------- */
  function initAutoDismissAlerts() {
    document.querySelectorAll('.alert-success').forEach(function (alert) {
      if (!alert.querySelector('.btn-close')) return;
      setTimeout(function () {
        alert.style.transition = 'opacity .4s ease';
        alert.style.opacity = '0';
        setTimeout(() => alert.remove(), 400);
      }, 5000);
    });
  }

  /* --------------------------------------------------------
     7. Tooltip nos botões desabilitados
     Bootstrap tooltips para botões disabled.
  -------------------------------------------------------- */
  function initTooltips() {
    if (typeof bootstrap === 'undefined') return;
    const tooltipEls = document.querySelectorAll('[title]:not([title=""])');
    tooltipEls.forEach(function (el) {
      new bootstrap.Tooltip(el, { trigger: 'hover focus' });
    });
  }

  /* --------------------------------------------------------
     Bootstrap
  -------------------------------------------------------- */
  document.addEventListener('DOMContentLoaded', function () {
    initRipple();
    initFormLoading();
    initPageTransition();
    initActiveNavLink();
    initConfirmForms();
    initAutoDismissAlerts();
    initTooltips();
  });

})();
