import {css, html, LitElement} from "lit";
import {Task} from "@lit/task";

class Notes extends LitElement {
    static properties = {
        modalType: {type: String},
        topic: {type: String},
        dateAdd: {type: String},
        dateMod: {type: String},
        user: {type: String},
        text: {type: String},
        uuid: {type: String},
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
        task: async () => {
            const params = new URLSearchParams(window.location.search);
            const response = await fetch(`http://localhost:8080/city/${params.get('cityId')}/notes`, {
                credentials: 'include'
            });
            if (!response.ok) {
                window.location.replace("/login");
            }
            return response.json();
        },
        args: () => []
    });

    render() {
        return html`
            <input type="button" onclick="location.href='/';" value="<< Back"/>
            <button @click=${() => this.modalType = 'NEW'}>Add note</button>
            <h1>Notes</h1>
            ${this.notesList()}
            ${this.modalType === 'NEW' ? this.newModal() : ''}
            ${this.modalType === 'VIEW' ? this.viewModal() : ''}
            ${this.modalType === 'EDIT' ? this.editModal() : ''}
        `
    }

    notesList() {
        return html`
            <table style="width:100%">
                <tr>
                    <th>Topic</th>
                    <th>Creation date</th>
                    <th>Modification date</th>
                    <th>User</th>
                    <th>Actions</th>
                </tr>
                ${this.notesTask.render({
                    // pending: () => html`<p>Loading notes ...</p>`,
                    complete: (notes) => {
                        return notes.map((item) => html`
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
                                    }}>Details
                                    </button>
                                    <button ?disabled=${item.user !== sessionStorage.getItem('USER')}
                                            @click=${() => {
                                                this.modalType = 'EDIT'
                                                this.topic = item.topic
                                                this.dateAdd = item.dateAdd
                                                this.dateMod = item.dateMod
                                                this.user = item.user
                                                this.text = item.text
                                                this.uuid = item.uuid
                                            }}>Edit
                                    </button>
                                </td>
                            </tr>
                        `)
                    },
                    error: (e) => html`<p>Error: ${e}</p>`
                })
                }
            </table>
        `
    }

    newModal() {
        return html`
            <modal-simple>
                <span class="close-btn" @click=${this.closeModal}>&times;</span>
                <h2>Note details</h2>
                <div>
                    <label for="topic">Topic</label><br>
                    <input type="text" id="topic" name="topic" value="">
                </div>
                <div>
                    <label for="text">Note</label>
                </div>
                <div>
                    <textarea id="text" name="text" rows="25" cols="200"></textarea>
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
                                user: sessionStorage.getItem('USER'),
                                text: this.renderRoot.querySelector('#text').value
                            }),
                            credentials: 'include'
                        });
                        if (response.ok) {
                            this.closeModal()
                        } else {
                            alert('Save failed')
                        }
                    }}>Save
                    </button>
                </div>
            </modal-simple>
        `
    }

    viewModal() {
        return html`
            <modal-simple>
                <span class="close-btn" @click=${this.closeModal}>&times;</span>
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
                    <textarea id="text" name="text" rows="25" cols="200" disabled>${this.text}</textarea>
                </div>
            </modal-simple>
        `
    }

    editModal() {
        return html`
            <modal-simple>
                <span class="close-btn" @click=${this.closeModal}>&times;</span>
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
                    <textarea id="text" name="text" rows="25" cols="200">${this.text}</textarea>
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
                                text: this.renderRoot.querySelector('#text').value
                            }),
                            credentials: 'include'
                        });
                        if (response.ok) {
                            this.closeModal()
                        } else {
                            alert('Save failed')
                        }
                    }}>Save
                    </button>
                </div>
            </modal-simple>
        `
    }

    closeModal() {
        this.modalType = '';
    }

}

customElements.define('notes-list', Notes);
