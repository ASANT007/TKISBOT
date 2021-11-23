  <!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>Thyssenkrupp Industrial Solutions India Pvt Ltd</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/custom.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="vendor/hover/effects.min.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/chart.min.js"></script>
<script src="js/bootstrap.js"></script>

<style>
.header {
	position: sticky;
	top:0;
}
body {
	height: 800px;
}
</style>

</head>
<!--<body onload="recommendation_summary();top_IFA_AUM_base();top_IFA_on_investors_base();topIFAonRecommadation()">-->
<body>
<!--header start-->
<div class="header-top">
  <div class="container">
    <div class="row">
      <div class="col-md-6 col-sm-6 col-6 centerdiv">
        <div class="logo"> <img src="images/thyssenkrupp-logo.jpg"  class="img-responsive logoimg"> </div>
      </div>
      <div class="col-md-6 col-sm-6 col-6  heading">QED BOT</div>
    </div>
    <!--header end-->
  </div>
</div>
<div class="container-fluid p-0"><!-- #BeginLibraryItem "/Library/topnav.lbi" --><div class="top_nav"> <span class="top_nav_trigger">Menu</span>
  <nav class="top_nav_links">
    <ul>
      <li class="topnav-first"><a class="topnav_home" href="dashboard.html">Dashboard</a></li>
      <li><a  href="project-tracking.html">View Project Details</a></li>
      <li><a  href="login.html">Logout</a></li>
    </ul>
  </nav>

</div><!-- #EndLibraryItem --></div>
</div>
<div class="container-fluid px-4">
  <div class="row" style="font-size:16px; padding:5px 2px;">
    <div class="col-md-12 col-sm-12" >
      <div class="login_user">Welcome <span class="user-name">Sameer Shirur</span>. You  are logged in as <span  class="user-name">Mgmt User</span> </div>
    </div>
  </div>
  <h2>Inconsistencies v/s Time</h2>
  
    <div class="content-area">
  <canvas id="myChart" style="width:100%;max-width:700px;margin:0 auto;padding:10px;"></canvas>

<script>
$(document).ready(function() {

	//get canvas
	
	var ctx = $("#myChart");

	var chart = new Chart( ctx, {
		type : "line",		
			var actions = [
			  {
			    name: 'Randomize',
			    handler(chart) {
			      chart.data.datasets.forEach(dataset => {
			        dataset.data.forEach(function(dataObj, j) {
			          const newVal = Utils.rand(0, 100);
			
			          if (typeof dataObj === 'object') {
			            dataObj.y = newVal;
			          } else {
			            dataset.data[j] = newVal;
			          }
			        });
			      });
			      chart.update();
			    }
			  },
			];
			
			var data = {
			  datasets: [{
			    label: 'Dataset with string point data',
			    backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
			    borderColor: Utils.CHART_COLORS.red,
			    fill: false,
			    data: [{
			      x: Utils.newDateString(0),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDateString(2),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDateString(4),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDateString(6),
			      y: Utils.rand(0, 100)
			    }],
			  }, {
			    label: 'Dataset with date object point data',
			    backgroundColor: Utils.transparentize(Utils.CHART_COLORS.blue, 0.5),
			    borderColor: Utils.CHART_COLORS.blue,
			    fill: false,
			    data: [{
			      x: Utils.newDate(0),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDate(2),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDate(5),
			      y: Utils.rand(0, 100)
			    }, {
			      x: Utils.newDate(6),
			      y: Utils.rand(0, 100)
			    }]
			  }]
			};
			
			 var config = {
			  type: 'line',
			  data: data,
			  options: {
			    spanGaps: 1000 * 60 * 60 * 24 * 2, // 2 days
			    responsive: true,
			    interaction: {
			      mode: 'nearest',
			    },
			    plugins: {
			      title: {
			        display: true,
			        text: 'Chart.js Time - spanGaps: 172800000 (2 days in ms)'
			      },
			    },
			    scales: {
			      x: {
			        type: 'time',
			        display: true,
			        title: {
			          display: true,
			          text: 'Date'
			        },
			        ticks: {
			          autoSkip: false,
			          maxRotation: 0,
			          major: {
			            enabled: true
			          },
			          // color: function(context) {
			          //   return context.tick && context.tick.major ? '#FF0000' : 'rgba(0,0,0,0.1)';
			          // },
			          font: function(context) {
			            if (context.tick && context.tick.major) {
			              return {
			                weight: 'bold',
			              };
			            }
			          }
			        }
			      },
			      y: {
			        display: true,
			        title: {
			          display: true,
			          text: 'value'
			        }
			      }
			    }
			  }
			};		


	};

});
    </script>
  
  

  
  
</div>
<div class="container-fluid">
  <div class="row">
    <div class="footer"> &copy  thyssenkrupp Industrial Solutions India Pvt Ltd </div>
  </div>
</div>
</body>
</html>

