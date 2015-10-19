<%@ page import="com.admu.accountinggroup.domain.*" %>
<%@ page import="com.admu.accountinggroup.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Benchmark</title>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("visualization", "1.1", {packages: ["bar"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Scenario - in ms', 'Normal Select', 'MongoDB', 'Table View'],
                ['A', isEmptyObject(${session.results.normal}, 0), isEmptyObject(${session.results.mongo}, 0), isEmptyObject(${session.results.tableView}, 0) ],
                ['B', 117081, 460811, 250881],
                ['C', 660991, 112091, 300991],
            ]);

            var options = {
                chart: {
                    title: 'Benchmark',
                    subtitle: 'Performance comparison against different storage technologies',
                }
            };

            var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

            chart.draw(data, options);
        }

        function isEmptyObject(value, defaultValue){
            return $.isEmptyObject(value) == false ? defaultValue : value;
        }
    </script>
</head>
<body>
<div class="content scaffold-list" role="main">
    <div class="nav" role="navigation">
        <ul>
            <li><g:link class="home" action="index">Back</g:link></li>
            <li><g:link class="create" action="entryAdd">Add T-Account Entry</g:link></li>
            <li><g:link class="home" action="approvedEntries">Approved Entries List</g:link></li>
        </ul>
    </div>
    <div id="columnchart_material" style="width: 900px; height: 500px; padding-left: 50px; padding-top: 50px"></div>
</div>
</body>
</html>