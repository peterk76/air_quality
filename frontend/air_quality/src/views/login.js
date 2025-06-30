import {css, html, LitElement} from 'lit';

class Login extends LitElement {

    static styles = css`
        form {
            display: flex;
            flex-direction: column;
            width: 300px;
        }
        input, button {
            margin: 8px 0;
        }
    `;

    render() {
        return html`
            <form @submit=${this._handleSubmit}>
                <input type="text" name="username" placeholder="Username" required/>
                <input type="password" name="password" placeholder="Password" required/>
                <button type="submit">Login</button>
            </form>
        `;
    }

    async _handleSubmit(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            body: new URLSearchParams(formData),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            credentials: 'include'
        })
        if (response.ok) {
            sessionStorage.setItem('USER', formData.get("username"))
            window.location.href = '/';
        } else {
            alert('Login failed');
        }
    }
}

customElements.define('login-view', Login);
