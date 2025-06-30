import {css, html, LitElement} from 'lit';

export class ModalSimple extends LitElement {
    static styles = css`
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 1000;
        }
        
        .modal-content {
            background: white;
            width: 75vw;
            height: 75vh;
            padding: 1rem;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
            overflow: auto;
        }
    `;

    render() {
        return html`
            <div class="modal-overlay">
                <div class="modal-content">
                    <slot></slot>
                </div>
            </div>`
    }
}
customElements.define('modal-simple', ModalSimple);
