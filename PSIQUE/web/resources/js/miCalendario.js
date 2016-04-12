
$(function () {
    
    //Ejemplos
    
    $('#basic').timepicker();
    $('#current').timepicker();
    $('#setTimeButton').on('click', function () {
        $('#current').timepicker('setTime', new Date());
    });
    $('#duracion').timepicker({
        'minTime': '2:00 PM',
        'maxTime': '11:30 PM',
        'showDuration': true
    });
    $('#disable').timepicker({
        'disableTimeRanges': [
            ['1am', '2 AM'],
            ['3am', '4:01 AM']
        ]
    });
    $('#step').timepicker({'step': 30});
    
    //Calendarios limitados
    
    $('#calendarioMañana').timepicker({
        'step': 30,
        'minTime': '9:30 AM',
        'maxTime': '11:30 AM'
    });
    
    $('#calendarioTarde').timepicker({
        'step': 30,
        'minTime': '12:30 PM',
        'maxTime': '4:30 PM'
    });
    
});