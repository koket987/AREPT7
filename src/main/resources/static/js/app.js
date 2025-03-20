// Define la URL base de tu back-end en EC2
const BASE_URL = 'http://ec2-44-201-89-115.compute-1.amazonaws.com:8080';

document.addEventListener('DOMContentLoaded', function() {
  // Elementos del DOM
  const authContainer = document.getElementById('auth-container');
  const appContainer = document.getElementById('app-container');
  const loginForm = document.getElementById('login-form');
  const registerForm = document.getElementById('register-form');
  const postForm = document.getElementById('post-form');
  const postContent = document.getElementById('post-content');
  const charCount = document.getElementById('char-count');
  const postsList = document.getElementById('posts-list');
  const usernameDisplay = document.getElementById('username-display');
  const logoutBtn = document.getElementById('logout-btn');

  // Verificación de existencia de elementos necesarios
  if (!authContainer || !appContainer || !loginForm || !registerForm || !postForm ||
      !postContent || !charCount || !postsList || !usernameDisplay || !logoutBtn) {
    console.error("Error: Uno o más elementos necesarios no se encuentran en el DOM.");
    return;
  }

  // Verificar si hay un usuario logueado
  const currentUser = JSON.parse(localStorage.getItem('currentUser'));
  if (currentUser) {
    showApp(currentUser);
  }

  // Event listeners
  loginForm.addEventListener('submit', handleLogin);
  registerForm.addEventListener('submit', handleRegister);
  postForm.addEventListener('submit', handlePostSubmit);
  postContent.addEventListener('input', updateCharCount);
  logoutBtn.addEventListener('click', handleLogout);

  // Cargar posts al inicio (si hay usuario logueado)
  if (currentUser) {
    loadPosts();
  }

  // Funciones de autenticación
  async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    try {
      const response = await fetch(`${BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      const data = await response.json();
      if (data.success) {
        const user = { id: data.userId, username: data.username };
        localStorage.setItem('currentUser', JSON.stringify(user));
        showApp(user);
        loadPosts();
      } else {
        alert(data.message || 'Error al iniciar sesión');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al iniciar sesión');
    }
  }

  async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;
    try {
      const response = await fetch(`${BASE_URL}/api/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      const data = await response.json();
      if (data.success) {
        alert('Registro exitoso. Por favor inicia sesión.');
        document.getElementById('register-username').value = '';
        document.getElementById('register-password').value = '';
        document.getElementById('login-username').value = username;
        document.getElementById('login-password').focus();
      } else {
        alert(data.message || 'Error al registrarse');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al registrarse');
    }
  }

  function handleLogout() {
    localStorage.removeItem('currentUser');
    showAuth();
  }

  // Funciones para posts
  async function handlePostSubmit(e) {
    e.preventDefault();
    const content = postContent.value.trim();
    if (!content) return;
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (!currentUser) {
      alert('Debes iniciar sesión para publicar');
      return;
    }
    try {
      const response = await fetch(`${BASE_URL}/api/posts`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content, userId: currentUser.id })
      });
      const data = await response.json();
      if (data.success) {
        postContent.value = '';
        updateCharCount();
        loadPosts();
      } else {
        alert(data.message || 'Error al crear el post');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al crear el post');
    }
  }

  async function loadPosts() {
    try {
      const response = await fetch(`${BASE_URL}/api/posts`);
      const data = await response.json();
      if (data.success) {
        renderPosts(data.posts);
      } else {
        console.error('Error al cargar los posts:', data.message);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }

  async function handleReplySubmit(e, postId) {
    e.preventDefault();
    const form = e.target;
    const replyContent = form.querySelector('.reply-content').value.trim();
    if (!replyContent) return;
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (!currentUser) {
      alert('Debes iniciar sesión para responder');
      return;
    }
    try {
      const response = await fetch(`${BASE_URL}/api/posts`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          content: replyContent,
          userId: currentUser.id,
          parentPostId: postId
        })
      });
      const data = await response.json();
      if (data.success) {
        form.querySelector('.reply-content').value = '';
        toggleReplyForm(postId);
        loadReplies(postId);
      } else {
        alert(data.message || 'Error al crear la respuesta');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al crear la respuesta');
    }
  }

  async function loadReplies(postId) {
    const repliesContainer = document.getElementById(`replies-${postId}`);
    if (!repliesContainer) return;
    try {
      const response = await fetch(`${BASE_URL}/api/posts/${postId}/replies`);
      const data = await response.json();
      if (data.success) {
        repliesContainer.innerHTML = '';
        if (data.replies.length === 0) {
          repliesContainer.innerHTML = '<p class="no-replies">No hay respuestas aún</p>';
          return;
        }
        data.replies.forEach(reply => {
          const replyEl = document.createElement('div');
          replyEl.className = 'reply';
          replyEl.innerHTML = `
            <div class="post-header">
              <span class="post-username">@${reply.username}</span>
              <span class="post-date">${formatDate(reply.createdAt)}</span>
            </div>
            <div class="post-content">${reply.content}</div>
          `;
          repliesContainer.appendChild(replyEl);
        });
      } else {
        console.error('Error al cargar las respuestas:', data.message);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }

  // Funciones de utilidad
  function showApp(user) {
    authContainer.classList.add('hidden');
    appContainer.classList.remove('hidden');
    usernameDisplay.textContent = user.username;
  }

  function showAuth() {
    appContainer.classList.add('hidden');
    authContainer.classList.remove('hidden');
    loginForm.reset();
    registerForm.reset();
  }

  function updateCharCount() {
    const remaining = 140 - postContent.value.length;
    charCount.textContent = `${remaining} caracteres restantes`;
    if (remaining < 0) {
      charCount.classList.add('error');
    } else {
      charCount.classList.remove('error');
    }
  }

  function renderPosts(posts) {
    postsList.innerHTML = '';
    if (posts.length === 0) {
      postsList.innerHTML = '<p class="no-posts">No hay posts aún</p>';
      return;
    }
    posts.forEach(post => {
      if (!post.parentPostId) { // Mostrar solo posts principales
        const postEl = document.createElement('div');
        postEl.className = 'post';
        postEl.setAttribute('data-post-id', post.id);
        postEl.innerHTML = `
          <div class="post-header">
            <span class="post-username">@${post.username}</span>
            <span class="post-date">${formatDate(post.createdAt)}</span>
          </div>
          <div class="post-content">${post.content}</div>
          <div class="post-actions">
            <button class="reply-btn" onclick="toggleReplyForm(${post.id})">Responder</button>
          </div>
          <div class="reply-form hidden" id="reply-form-${post.id}">
            <form onsubmit="handleReplySubmit(event, ${post.id})">
              <textarea class="reply-content" maxlength="140" placeholder="Escribe tu respuesta" required></textarea>
              <button type="submit">Responder</button>
            </form>
          </div>
          <div class="replies" id="replies-${post.id}"></div>
        `;
        postsList.appendChild(postEl);
        loadReplies(post.id);
      }
    });
  }

  function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
  }

  // Funciones globales para eventos en HTML
  window.toggleReplyForm = function(postId) {
    const replyForm = document.getElementById(`reply-form-${postId}`);
    replyForm.classList.toggle('hidden');
  };
  window.handleReplySubmit = handleReplySubmit;

  // Logs para depuración
  console.log("loginForm:", document.getElementById('login-form'));
  console.log("logoutBtn:", document.getElementById('logout-btn'));
});
