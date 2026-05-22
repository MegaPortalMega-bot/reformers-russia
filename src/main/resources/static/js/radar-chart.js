document.addEventListener("DOMContentLoaded", function () {
  const canvas = document.getElementById("ratingsRadar");
  if (!canvas || typeof Chart === "undefined") {
    return;
  }

  const scoresRaw = canvas.getAttribute("data-scores");
  if (!scoresRaw) {
    return;
  }

  const scores = scoresRaw.split(",").map(function (value) {
    return Number.parseInt(value, 10);
  });

  const reformerName = canvas.getAttribute("data-name") || "Реформатор";

  new Chart(canvas, {
    type: "radar",
    data: {
      labels: [
        "Экономика",
        "Военные",
        "Социальные",
        "Образование",
        "Госуправление",
      ],
      datasets: [
        {
          label: reformerName,
          data: scores,
          backgroundColor: "rgba(54, 162, 235, 0.35)",
          borderColor: "rgba(54, 162, 235, 0.9)",
          borderWidth: 2,
          pointBackgroundColor: "rgba(54, 162, 235, 1)",
          pointBorderColor: "#fff",
          pointHoverBackgroundColor: "#fff",
          pointHoverBorderColor: "rgba(54, 162, 235, 1)",
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: true,
      scales: {
        r: {
          min: 0,
          max: 5,
          beginAtZero: true,
          ticks: {
            stepSize: 1,
            backdropColor: "transparent",
          },
          pointLabels: {
            font: {
              size: 12,
            },
          },
        },
      },
      plugins: {
        legend: {
          display: false,
        },
      },
    },
  });
});
