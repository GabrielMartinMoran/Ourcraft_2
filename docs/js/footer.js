export const footer = {
    render: () => {
        document.getElementById('footer').innerHTML = /*html*/`
        <br>
        <div class="footer">
            <label style="padding-left: 10px;">© ${new Date().getFullYear()} Copyright: Gabriel Martín Moran</label>
            <label class="float-right" style="margin-right: 10px;">Versión: 1.0.0</label>
        </div>
        `;
    }
}