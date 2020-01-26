$(document).ready(function() {
  drawGraph('weekGraph', 'By Week', weekData, createWeekLabels(weekData));
  drawGraph('monthGraph', 'By Month', monthData, createMonthLabels(monthData));
});

function createWeekLabels(data) {
    var labels = [];
    for (var i = 0; i < data.profitData.length; i++) {
        labels.push('Week ' + (i+1));
    }
    return labels;
}

function createMonthLabels(data) {
    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var labels = [];
    for (var i = 0; i < data.profitData.length; i++) {
        labels.push(months[i]);
    }
    return labels;
}

function drawGraph(selector, title, data, labels) {
    var ctx = document.getElementById(selector);
    var lineGraph = new Chart(ctx, {
        type: 'bar',
        data: {
            datasets: [{
                type: 'line',
                data: data.profitData,
                fill: false,
                label: 'Profit',
                borderColor: 'rgba(74, 189, 172, 1)',
                pointBackgroundColor: 'rgba(74, 189, 172, 1)'
            }, {
                type: 'line',
                data: data.wagerData,
                fill: false,
                label: 'Wagered',
                borderColor: 'rgba(217, 51, 39, 0.6)',
                pointBackgroundColor: 'rgba(217, 51, 39, 0.6)',
                hidden: true
            }, {
                data: data.sportsBettingData,
                label: 'Sports Betting',
                backgroundColor: 'rgba(61, 124, 212, 1)'
            }, {
                data: data.pokerData,
                label: 'Poker',
                backgroundColor: 'rgba(247, 183, 51, 1)'
            }, {
                data: data.otherData,
                label: 'Other',
                backgroundColor: 'rgba(100, 78, 166, 1)'
            }],
            labels: labels
        },
        options: {
            maintainAspectRatio: false,
            legend: {
                position: 'bottom'
            },
            title: {
                display: true,
                fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"',
                color: 'rgba(80, 81, 79, 1)',
                fontSize: 16,
                text: title
            },
            scales: {
                xAxes: [{
                    stacked: true
                }],
                yAxes: [{
                    stacked: true,
                    ticks: {
                        callback: function(value, index, values) {
                            var prefix = '';

                            if (0 > value) {
                                prefix = '-';
                            }

                            return prefix + '$' + Math.abs(value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                        }
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    label: function(tooltip, obj) {
                        var prefix = '';

                        if (0 > tooltip.yLabel) {
                            prefix = '-';
                        }

                        return obj.datasets[tooltip.datasetIndex].label + ': ' + prefix + '$' + Math.abs(tooltip.yLabel).toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    }
                }
            }
        }
    });
}