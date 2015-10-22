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
                ['Real Figure', 'Read', 'Write'],
                ['Normal SQL', isEmptyObject(${session.results.normalRead}, 0), isEmptyObject(${session.results.normalWrite}, 0)],
                ['MongoDB', isEmptyObject(${session.results.mongoRead}, 0), isEmptyObject(${session.results.mongoWrite})],
                ['Table View', isEmptyObject(${session.results.tableViewRead}, 0), isEmptyObject(${session.results.tableViewWrite}, 0)],
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
            return ($.isEmptyObject(value) == false ? defaultValue : value);
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
    <br/>
    <table>
        <thead>
            <th>Storage Technology</th>
            <th>Read</th>
            <th>Write</th>
        </thead>
        <tbody>
            <tr>
                <td>Normal SQL</td>
                <td>${session.results.normalRead}</td>
                <td>${session.results.normalWrite}</td>
            </tr>
            <tr>
                <td>MongoDB</td>
                <td>${session.results.mongoRead}</td>
                <td>${session.results.mongoWrite}</td>
            </tr>
            <tr>
                <td>Table View</td>
                <td>${session.results.tableViewRead}</td>
                <td>${session.results.tableViewWrite}</td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>