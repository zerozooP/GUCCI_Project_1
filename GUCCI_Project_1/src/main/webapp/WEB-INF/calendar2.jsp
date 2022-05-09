<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>

        <title>일정</title>
        <style type="text/css">
		    body {
		        margin: 40px 10px;
		        padding: 0;
		        font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		        font-size: 14px;
		    }
		    
		    
		
		    #calendar {
		        max-width: 900px;
		        margin: 0 auto;
		        color: black;
		        text-decoration:none;
		    }
		   
		    .fc-day-number.fc-sat.fc-past { color:#0000FF; }     /* 토요일 */
		    .fc-day-number.fc-sun.fc-past { color:#FF0000; }    /* 일요일 */
		    
		</style>

        
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.css">	
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/locales-all.js"></script>
    </head>
    <body class="sb-nav-fixed">
		<jsp:include page="/WEB-INF/topnav.jsp"></jsp:include>
		
        <div id="layoutSidenav">
			<jsp:include page="/WEB-INF/sidenav.jsp"></jsp:include>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Calendar</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-calendar me-1"></i>
                                calendar
                            </div>
                            <div class="card-body">
								<div id='calendar'></div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
            </div>
        </div>
		<script>
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
				locale: 'ko' // 한국어 설정
			});
			calendar.render();
		});
		</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>
