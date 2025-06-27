import {css, html, LitElement} from "lit";
import {Task} from "@lit/task";

class Notes extends LitElement {
    static properties = {
        modalType: { type: String },
        topic: { type: String },
        dateAdd: { type: String },
        dateMod: { type: String },
        user: { type: String },
        text: { type: String },
        uuid: { type: String }
    };

    static styles = css`
        table, th, td {
            border: 1px solid black;
        }
        .close-btn {
            float: right;
            cursor: pointer;
            font-size: 1.2rem;
        }
    `;

    notesTask = new Task(this, {
        task: async ([], {signal}) => {
            const params = new URLSearchParams(window.location.search);
            const response = await fetch(`http://localhost:8080/city/${params.get('cityId')}/notes`, {signal});
            if (!response.ok) {
                window.location.replace("/login");
            }
            return response.json();
        },
        args: () => []
    });

    render() {
        return this.notesTask.render({
            pending: () => html`<p>Loading notes ...</p>`,
            complete: (notes) => { // TODO small modal
                return html`
                    <input type="button" onclick="location.href='/';" value="<< Back"/>
                    <button @click=${() => this.modalType = 'NEW'}>Add note</button>
                    <h1>Notes</h1>
                    <table style="width:100%">
                        <tr>
                            <th>Topic</th>
                            <th>Creation date</th>
                            <th>Modification date</th>
                            <th>User</th>
                            <th>Actions</th>
                        </tr>
                        ${notes.map((item) => html`
                            <tr>
                                <td>${item.topic}</td>
                                <td>${item.dateAdd}</td>
                                <td>${item.dateMod}</td>
                                <td>${item.user}</td>
                                <td>
                                    <button @click=${() => {
                                        this.modalType = 'VIEW'
                                        this.topic = item.topic
                                        this.dateAdd = item.dateAdd
                                        this.dateMod = item.dateMod
                                        this.user = item.user
                                        this.text = item.text
                                        this.uuid = item.uuid
                                    }}>Details</button>
                                    <button @click=${() => { // TODO active only for creator
                                        this.modalType = 'EDIT'
                                        this.topic = item.topic
                                        this.dateAdd = item.dateAdd
                                        this.dateMod = item.dateMod
                                        this.user = item.user
                                        this.text = item.text
                                        this.uuid = item.uuid
                                    }}>Edit</button>
                                </td>
                            </tr>
                        `)}
                    </table>
                    ${this.modalType === 'NEW' ? html`
                        <modal-simple>
                            <span class="close-btn" @click=${this._close}>&times;</span>
                            <h2>Note details</h2>
                            <div>
                                <label for="topic">Topic</label><br>
                                <input type="text" id="topic" name="topic" value="">
                            </div>
                            <div>
                                <label for="text">Note</label>
                            </div>
                            <div>
                                <textarea id="text" name="text" rows="30" cols="250"></textarea>
                            </div>
                            <div>
                                <button @click=${async () => {
                                    const params = new URLSearchParams(window.location.search);
                                    const response = await fetch('http://localhost:8080/city/note/add', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify({ 
                                            cityId: params.get('cityId'), 
                                            topic: this.renderRoot.querySelector('#topic').value, 
                                            user: 'admin', // TODO get user
                                            text: this.renderRoot.querySelector('#text').value })
                                    });
                                    if (response.ok) {
                                        this._close()
                                        this.requestUpdate() // TODO update view
                                    } else {
                                        alert('Save failed')
                                    }
                                }}>Save</button>
                            </div>
                        </modal-simple>
                    ` : ''}
                    ${this.modalType === 'VIEW' ? html`
                        <modal-simple>
                            <span class="close-btn" @click=${this._close}>&times;</span>
                            <h2>Note details</h2>
                            <div>
                                <label for="topic">Topic</label><br>
                                <input type="text" id="topic" name="topic" value=${this.topic} disabled>
                            </div>
                            <div>
                                <label for="dateAdd">Creation date</label><br>
                                <input type="text" id="dateAdd" name="dateAdd" value=${this.dateAdd} disabled>
                            </div>
                            <div>
                                <label for="dateMod">Modification date</label><br>
                                <input type="text" id="dateMod" name="dateMod" value=${this.dateMod} disabled>
                            </div>
                            <div>
                                <label for="user">User</label><br>
                                <input type="text" id="user" name="user" value=${this.user} disabled>
                            </div>
                            <div>
                                <label for="text">Note</label>
                            </div>
                            <div>
                                <textarea id="text" name="text" rows="30" cols="250" disabled>${this.text}</textarea>
                            </div>
                        </modal-simple>
                    ` : ''}
                    ${this.modalType === 'EDIT' ? html`
                        <modal-simple>
                            <span class="close-btn" @click=${this._close}>&times;</span>
                            <h2>Note details</h2>
                            <div>
                                <label for="topic">Topic</label><br>
                                <input type="text" id="topic" name="topic" value=${this.topic} disabled>
                            </div>
                            <div>
                                <label for="dateAdd">Creation date</label><br>
                                <input type="text" id="dateAdd" name="dateAdd" value=${this.dateAdd} disabled>
                            </div>
                            <div>
                                <label for="dateMod">Modification date</label><br>
                                <input type="text" id="dateMod" name="dateMod" value=${this.dateMod} disabled>
                            </div>
                            <div>
                                <label for="user">User</label><br>
                                <input type="text" id="user" name="user" value=${this.user} disabled>
                            </div>
                            <div>
                                <label for="text">Note</label>
                            </div>
                            <div>
                                <textarea id="text" name="text" rows="30" cols="250">${this.text}</textarea>
                            </div>
                            <div>
                                <button @click=${async () => {
                                    const response = await fetch('http://localhost:8080/city/note/edit', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify({ 
                                            uuid: this.uuid, 
                                            text: this.renderRoot.querySelector('#text').value })
                                    });
                                    if (response.ok) {
                                        this._close()
                                        this.requestUpdate() // TODO update view
                                    } else {
                                        alert('Save failed')
                                    }
                                }}>Save</button>
                            </div>
                        </modal-simple>
                    ` : ''}
                `
            },
            error: (e) => html`<p>Error: ${e}</p>`
        });
    }

    _close() {
        this.modalType = '';
    }

}
customElements.define('notes-list', Notes);
