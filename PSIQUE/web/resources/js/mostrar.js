function mostrarJornada(id) {
    if (id == 1) {
        $("#Mañana").show();
        $("#Tarde").hide();
    }

    if (id == 2) {
        $("#Mañana").hide();
        $("#Tarde").show();
    }
    if (id == 3) {
        $("#cliente").hide();
        $("#proveedor").hide();
        $("#instructor").show();
    }
}

function mostrarBoton(id) {
    if (id == "nombreEmpresa") {
        $("#boton1").show();
    }
    if (id == "proveedor") {
        $("#cliente").hide();
        $("#proveedor").show();
        $("#boton1").hide();
    }
    if (id == "instructor") {
        $("#cliente").hide();
        $("#proveedor").show();
        $("#boton1").hide();
    }
}

PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText: 'Todo el día'
};