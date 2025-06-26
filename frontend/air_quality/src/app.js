import {html, LitElement} from 'lit';
import {Router} from '@vaadin/router';

import './views/login.js';
import './views/no2-worst-list.js';
import './views/notes-list.js';

class MainApp extends LitElement {
    render() {
        return html`
            <div id="outlet"></div>
        `;
    }

    firstUpdated() {
        const router = new Router(this.renderRoot.querySelector('#outlet'));
        router.setRoutes([
            {path: '/login', component: 'login-view'},
            {path: '/', component: 'no2-worst-list'},
            {path: '/notes', component: 'notes-list'},
        ]);
    }
}

customElements.define('main-app', MainApp);
