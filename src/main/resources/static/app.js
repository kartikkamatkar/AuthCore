const api = (path) => `/auth${path}`;

function getToken() {
  return localStorage.getItem('token') || '';
}

function setStatus(element, message, kind = '') {
  if (!element) {
    return;
  }
  element.textContent = message;
  element.className = `notice${kind ? ` ${kind}` : ''}`;
}

async function readResponse(response) {
  const text = await response.text();
  return text || '';
}

async function handleJsonPost(path, payload) {
  const response = await fetch(api(path), {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...(getToken() ? { Authorization: `Bearer ${getToken()}` } : {})
    },
    body: JSON.stringify(payload)
  });

  const message = await readResponse(response);
  if (!response.ok) {
    throw new Error(message || 'Request failed');
  }
  return message;
}

function getFormValues(form) {
  return Object.fromEntries(new FormData(form).entries());
}

function attachAuthLinks(root) {
  if (!root) {
    return;
  }
  root.querySelectorAll('[data-nav]').forEach((link) => {
    const target = link.getAttribute('data-nav');
    if (target) {
      link.addEventListener('click', () => {
        window.location.href = target;
      });
    }
  });
}

function initRegisterPage() {
  const form = document.querySelector('[data-register-form]');
  const status = document.querySelector('[data-status]');
  if (!form) {
    return;
  }

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const data = getFormValues(form);
    const submitButton = form.querySelector('button[type="submit"]');

    try {
      submitButton.disabled = true;
      setStatus(status, 'Creating your account...', '');
      const message = await handleJsonPost('/register', {
        name: data.name,
        email: data.email,
        password: data.password
      });
      setStatus(status, message || 'Registration successful. Check your inbox for the OTP.', 'success');
      window.location.href = `/otp-verify?email=${encodeURIComponent(data.email)}`;
    } catch (error) {
      setStatus(status, error.message || 'Registration failed', 'error');
    } finally {
      submitButton.disabled = false;
    }
  });
}

function initLoginPage() {
  const form = document.querySelector('[data-login-form]');
  const status = document.querySelector('[data-status]');
  if (!form) {
    return;
  }

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const data = getFormValues(form);
    const submitButton = form.querySelector('button[type="submit"]');

    try {
      submitButton.disabled = true;
      setStatus(status, 'Signing you in...', '');
      const token = await handleJsonPost('/login', {
        email: data.email,
        password: data.password
      });
      if ((token || '').toLowerCase().includes('otp sent')) {
        setStatus(status, 'OTP sent. Check your email to continue.', 'success');
        window.location.href = `/otp-verify?email=${encodeURIComponent(data.email)}`;
        return;
      }

      localStorage.setItem('token', token);
      localStorage.setItem('authcore-user', JSON.stringify({
        name: data.email || '',
        email: data.email || ''
      }));
      setStatus(status, 'Login successful. Redirecting...', 'success');
      window.location.href = '/dashboard';
    } catch (error) {
      setStatus(status, error.message || 'Login failed', 'error');
    } finally {
      submitButton.disabled = false;
    }
  });
}

function initOtpPage() {
  const form = document.querySelector('[data-otp-form]');
  const status = document.querySelector('[data-status]');
  if (!form) {
    return;
  }

  const emailInput = form.querySelector('input[name="email"]');
  const params = new URLSearchParams(window.location.search);
  const email = params.get('email') || '';
  if (emailInput && email) {
    emailInput.value = email;
  }

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const data = getFormValues(form);
    const submitButton = form.querySelector('button[type="submit"]');

    try {
      submitButton.disabled = true;
      setStatus(status, 'Verifying OTP...', '');
      const token = await handleJsonPost('/verifyotp', {
        email: data.email,
        otp: data.otp
      });
      localStorage.setItem('token', token);
      setStatus(status, 'OTP verified. Redirecting to the dashboard...', 'success');
      window.location.href = '/dashboard';
    } catch (error) {
      setStatus(status, error.message || 'OTP verification failed', 'error');
    } finally {
      submitButton.disabled = false;
    }
  });
}

function initForgotPasswordPage() {
  const form = document.querySelector('[data-forgot-form]');
  const status = document.querySelector('[data-status]');
  if (!form) {
    return;
  }

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const data = getFormValues(form);
    const submitButton = form.querySelector('button[type="submit"]');

    try {
      submitButton.disabled = true;
      setStatus(status, 'Sending reset OTP...', '');
      const message = await handleJsonPost('/forgetpass', {
        email: data.email
      });
      setStatus(status, message || 'Reset OTP sent. Continue to the reset page.', 'success');
      window.location.href = `/reset-password?email=${encodeURIComponent(data.email)}`;
    } catch (error) {
      setStatus(status, error.message || 'Could not send reset OTP', 'error');
    } finally {
      submitButton.disabled = false;
    }
  });
}

function initResetPasswordPage() {
  const form = document.querySelector('[data-reset-form]');
  const status = document.querySelector('[data-status]');
  if (!form) {
    return;
  }

  const emailInput = form.querySelector('input[name="email"]');
  const params = new URLSearchParams(window.location.search);
  const email = params.get('email') || '';
  if (emailInput && email) {
    emailInput.value = email;
  }

  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const data = getFormValues(form);
    const submitButton = form.querySelector('button[type="submit"]');

    try {
      submitButton.disabled = true;
      setStatus(status, 'Resetting password...', '');
      const message = await handleJsonPost('/resetpass', {
        email: data.email,
        otp: data.otp,
        newPassword: data.newPassword
      });
      setStatus(status, message || 'Password reset successful. You can sign in again.', 'success');
      window.location.href = '/login';
    } catch (error) {
      setStatus(status, error.message || 'Password reset failed', 'error');
    } finally {
      submitButton.disabled = false;
    }
  });
}

function initDashboardPage() {
  const userBadge = document.querySelector('[data-user-badge]');
  const token = getToken();
  const storedUser = localStorage.getItem('authcore-user');

  if (userBadge) {
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        userBadge.textContent = user.name || user.email || 'User';
      } catch {
        userBadge.textContent = 'User';
      }
    } else if (token) {
      userBadge.textContent = 'Authenticated User';
    } else {
      userBadge.textContent = 'Guest';
    }
  }

  document.querySelectorAll('[data-logout]').forEach((button) => {
    button.addEventListener('click', () => {
      localStorage.removeItem('token');
      localStorage.removeItem('authcore-user');
      window.location.href = '/login';
    });
  });
}

function initAdminPage() {
  document.querySelectorAll('[data-logout]').forEach((button) => {
    button.addEventListener('click', () => {
      localStorage.removeItem('token');
      localStorage.removeItem('authcore-user');
      window.location.href = '/login';
    });
  });
}

document.addEventListener('DOMContentLoaded', () => {
  attachAuthLinks(document);
  initRegisterPage();
  initLoginPage();
  initOtpPage();
  initForgotPasswordPage();
  initResetPasswordPage();
  initDashboardPage();
  initAdminPage();
});
