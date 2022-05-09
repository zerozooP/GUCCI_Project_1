<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.example.demo.vo.CalendarVO"%>
    
<!DOCTYPE html>
<html>
    <head>
    	<style>
		    body {
		        margin: 40px 10px;
		        padding: 0;
		        font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		        font-size: 14px;
		    }
		    
		    
		
		    #calendar {
		        max-width: 900px;
		        margin: 0 auto;

		    }
		   
		    .fc-day-number.fc-sat.fc-past { color:#0000FF; }     /* 토요일 */
		    .fc-day-number.fc-sun.fc-past { color:#FF0000; }    /* 일요일 */
    	</style>
    
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.css">	
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/locales-all.js"></script>
    </head>
    <body>

                <div id='calendar'></div>
				<script type="text/javascript">
				document.addEventListener('DOMContentLoaded', function() {
					var calendarEl = document.getElementById('calendar');
					var calendar = new FullCalendar.Calendar(calendarEl, {
						initialView : 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
						headerToolbar : { // 헤더에 표시할 툴 바
							start : 'prev next today',
							center : 'title',
							end : 'dayGridMonth,dayGridWeek,dayGridDay'
						},
						titleFormat : function(date) {
							return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
						},
						//initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
						selectable : true, // 달력 일자 드래그 설정가능
						droppable : true,
						editable : true,
						nowIndicator: true, // 현재 시간 마크
						locale: 'ko', // 한국어 설정
							events : [ 
					    	    <%List<CalendarVO> calendarList = (List<CalendarVO>) request.getAttribute("calendarList");%>
					            <%if (calendarList != null) {%>
					            <%for (CalendarVO vo : calendarList) {%>
					            {
					            	title : '<%=vo.getCalendar_title()%>',
					                start : '<%=vo.getCalendar_start()%>',
					                end : '<%=vo.getCalendar_end()%>',
					                color : '#' + Math.round(Math.random() * 0xffffff).toString(16)
					             },
						<%}
					}%>
									]
					});
					calendar.render();
				});
				</script>

    </body>
</html>
