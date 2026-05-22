document.addEventListener("DOMContentLoaded", function () {
  const input = document.getElementById("searchInput");
  const grid = document.getElementById("reformersGrid");
  const emptyState = document.getElementById("emptyState");
  const eraSelect = document.getElementById("eraFilter");
  const eraFormQuery = document.getElementById("eraFormQuery");
  const resetEraBtn = document.getElementById("resetEraFilter");
  const catalogCount = document.getElementById("catalogCount");

  if (!grid) {
    return;
  }

  function normalize(str) {
    return (str || "").toLowerCase();
  }

  function pluralizePersons(count) {
    const n = Math.abs(count) % 100;
    const n1 = n % 10;
    if (n > 10 && n < 20) {
      return count + " персон";
    }
    if (n1 > 1 && n1 < 5) {
      return count + " персоны";
    }
    if (n1 === 1) {
      return count + " персона";
    }
    return count + " персон";
  }

  function applyFilter() {
    const query = input ? normalize(input.value) : "";
    const selectedEra = eraSelect ? eraSelect.value : "";
    const cards = grid.querySelectorAll(".reformer-card");
    let visibleCount = 0;

    cards.forEach((card) => {
      const name = normalize(card.getAttribute("data-name"));
      const era = card.getAttribute("data-era") || "";
      const matchName = !query || name.includes(query);
      const matchEra = !selectedEra || era === selectedEra;
      const visible = matchName && matchEra;
      card.style.display = visible ? "" : "none";
      if (visible) {
        visibleCount++;
      }
    });

    if (catalogCount) {
      catalogCount.textContent = pluralizePersons(visibleCount);
    }

    if (emptyState) {
      emptyState.style.display = visibleCount === 0 ? "" : "none";
    }
  }

  if (input) {
    input.addEventListener("input", function () {
      if (eraFormQuery) {
        eraFormQuery.value = input.value;
      }
      applyFilter();
    });
  }

  if (eraSelect) {
    eraSelect.addEventListener("change", function () {
      if (eraFormQuery && input) {
        eraFormQuery.value = input.value;
      }
      applyFilter();
      const params = new URLSearchParams();
      if (eraSelect.value) {
        params.set("era", eraSelect.value);
      }
      if (input && input.value.trim()) {
        params.set("q", input.value.trim());
      }
      const queryString = params.toString();
      const newUrl = queryString ? "?" + queryString : window.location.pathname;
      window.history.replaceState({}, "", newUrl);
    });
  }

  if (resetEraBtn) {
    resetEraBtn.addEventListener("click", function () {
      if (eraSelect) {
        eraSelect.value = "";
      }
      if (input) {
        input.value = "";
      }
      if (eraFormQuery) {
        eraFormQuery.value = "";
      }
      applyFilter();
      window.history.replaceState({}, "", window.location.pathname);
    });
  }

  applyFilter();
});
