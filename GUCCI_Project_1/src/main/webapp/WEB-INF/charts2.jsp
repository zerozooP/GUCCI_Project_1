<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
        <title>Charts - SB Admin</title>

        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    </head>
    <body class="sb-nav-fixed">
		<jsp:include page="/WEB-INF/topnav.jsp"></jsp:include>
        <div id="layoutSidenav">
			<jsp:include page="/WEB-INF/sidenav.jsp"></jsp:include>

            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Charts</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
                            <li class="breadcrumb-item active">Charts</li>
                        </ol>
                        <div class="card mb-4">
                            <div class="card-body">
                                Chart.js is a third party plugin that is used to generate the charts in this template. The charts below have been customized - for further customization options, please visit the official
                                <a target="_blank" href="https://www.chartjs.org/docs/latest/">Chart.js documentation</a>
                                .
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-area me-1"></i>
                                Area Chart Example
                            </div>
                            <div class="card-body"><canvas id="myAreaChart" width="100%" height="30"></canvas></div>
                            <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        Bar Chart Example
                                    </div>
                                    <div class="card-body"><div id="columnchart_material" style="width: 100%; height: 50;"></div></canvas></div>
                                    <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-pie me-1"></i>
                                        Pie Chart Example
                                    </div>
                                    <div class="card-body"><div id="piechart_3d" style="width:100% ; height: 50;"></div></div>
                                    <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-map me-1"></i>
                                지도 그래프
                            </div>
                            <div class="card-body"><div id="chart_div" style="width: 100%; height: 500px;"></div></div>
                            <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                        </div>
                    </div>
                </main>
				<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="assets/demo/chart-area-demo.js"></script>
        <script src="assets/demo/chart-bar-demo.js"></script>
        <script src="assets/demo/chart-pie-demo.js"></script>
       	<script type="text/javascript">
	      google.charts.load("current", {packages:["corechart"]});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	        var data = google.visualization.arrayToDataTable([
	          ['Task', 'Hours per Day'],
	          ['Work',     11],
	          ['Eat',      2],
	          ['Commute',  2],
	          ['Watch TV', 2],
	          ['Sleep',    7]
	        ]);
	
	        var options = {
	          title: 'My Daily Activities',
	          is3D: true,
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
	        chart.draw(data, options);
	      }
	    </script>
	        <script type="text/javascript">
		      google.charts.load('current', {'packages':['bar']});
		      google.charts.setOnLoadCallback(drawChart);
		
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		          ['Year', 'Sales', 'Expenses', 'Profit'],
		          ['2014', 1000, 400, 200],
		          ['2015', 1170, 460, 250],
		          ['2016', 660, 1120, 300],
		          ['2017', 1030, 540, 350]
		        ]);
		
		        var options = {
		          chart: {
		            title: 'Company Performance',
		            subtitle: 'Sales, Expenses, and Profit: 2014-2017',
		          }
		        };
		
		        var chart = new google.charts.Bar(document.getElementById('columnchart_material'));
		
		        chart.draw(data, google.charts.Bar.convertOptions(options));
		      }
		    </script>
		    <script type='text/javascript'>
		     google.charts.load('current', {
		       'packages': ['geochart'],
		       // Note: Because markers require geocoding, you'll need a mapsApiKey.
		       // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
		       'mapsApiKey': 'AIzaSyDiWOeTX76sFIx3g0toEJHxnRU8ranyeuQ'
		     });
		     google.charts.setOnLoadCallback(drawMarkersMap);
		
		      function drawMarkersMap() {
		      var data = google.visualization.arrayToDataTable([
		        ['City',   'Population'],
		        ['Seoul',      9506778],
		        ['Busan',     3343504],
		        ['Daegu',     2380494],
		        ['Incheon',     2952699],
		        ['Gwangju',     1438463],
		        ['Daejeon',     1450057],
		        ['Ulsan',     1118010]
		      ]);
		
		      var options = {
		        region: 'KR',
		        displayMode: 'markers',
		        colorAxis: {colors: ['green', 'blue']}
		      };
		
		      var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));
		      chart.draw(data, options);
		    };
		    </script>
    </body>
</html>