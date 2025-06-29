import {Task} from '@lit/task';
import {css, html, LitElement} from "lit";

class No2WorstList extends LitElement {
    static styles = css`
        table, th, td {
            border: 1px solid black;
        }
    `;

    no2task = new Task(this, {
        task: async () => {
            const response = await fetch(`http://localhost:8080/api/report/worst-cities-no2-y2y`,
                {
                    credentials: 'include'
                }
            );
            if (!response.ok) {
                window.location.replace("/login");
            } else {
                return response.json();
            }
        },
        args: () => []
    });

    render() {
        return this.no2task.render({
            pending: () => html`<p>Loading cities ...</p>`,
            complete: (cities) => {
                return !cities
                    ? html``
                    : html`
                            <h1>Worst european NO2 cities</h1>
                            <table style="width:100%">
                                <tr>
                                    <th>Country</th>
                                    <th>City</th>
                                    <th>Average No2 last month</th>
                                    <th>Average No2 last year</th>
                                    <th>Actions</th>
                                </tr>
                                ${cities.map((item) => html`
                                    <tr>
                                        <td>${item.country}</td>
                                        <td>${item.city}</td>
                                        <td>${item.avgNo2Current}</td>
                                        <td>${item.avgNo2YearBefore}</td>
                                        <td><input type="button" onclick="location.href='/notes?cityId=${item.cityId}';"
                                                   value="Notes"/></td>
                                    </tr>
                                `)}
                            </table>`
            },
            error: (e) => html`<p>Error: ${e}</p>`
        });
    }
}

customElements.define('no2-worst-list', No2WorstList);
