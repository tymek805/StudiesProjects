document.getElementById('contact-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const confirmationMessage = document.getElementById('confirmationMessage');

    const form = event.target;

    if (form.checkValidity()) {
        confirmationMessage.classList.remove('d-none');
        setTimeout(() => {
            confirmationMessage.classList.add('show');
        }, 500);

        // form.submit();
        form.reset();

        setTimeout(() => {
            confirmationMessage.classList.remove('show');
            setTimeout(() => {
                confirmationMessage.classList.add('d-none');
            }, 500);
        }, 5000);
    } else {
        form.reportValidity();
    }
});

function openMap(city) {
    document.getElementById('googleMap').src = `https://www.google.com/maps?q=${city}&output=embed`;
    const mapSection = document.getElementById('map');

    mapSection.scrollIntoView({
        behavior: 'smooth'
    });
}

function scrollToFAQ() {
    const faqSection = document.getElementById('faq');
    faqSection.scrollIntoView({
        behavior: 'smooth'
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const chartContainer = document.getElementById('chartContainer');
    const ctx = document.getElementById('cityChart').getContext('2d');
    let chartInitialized = false;

    const months = ["Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
        "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"];

    const cityData = {
        "Szczecin": [45, 60, 72, 55, 80, 120, 150, 140, 125, 90, 70, 50],
        "Toruń": [30, 50, 65, 85, 100, 130, 140, 135, 110, 85, 60, 40],
        "Poznań": [55, 75, 95, 120, 165, 180, 200, 190, 160, 130, 100, 80],
        "Wrocław": [70, 90, 110, 130, 160, 190, 220, 210, 180, 140, 110, 85],
        "Gdańsk": [20, 40, 60, 80, 110, 140, 160, 155, 130, 100, 75, 45],
        "Kraków": [85, 105, 125, 145, 180, 210, 250, 240, 200, 170, 130, 100]
    };

    const colors = ["#FF5733", "#33FF57", "#3357FF", "#FF33A1", "#33FFF5", "#FF9F33"];

    const datasets = Object.keys(cityData).map((city, index) => ({
        label: city,
        data: cityData[city],
        borderColor: colors[index],
        borderWidth: 2,
        tension: 0.3,
        fill: false
    }));

    function initializeChart() {
        if (!chartInitialized) {
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: months,
                    datasets: datasets
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top'
                        }
                    },
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Miesiące',
                                font: { size: 14 }
                            }
                        },
                        y: {
                            title: {
                                display: true,
                                text: 'Wartości',
                                font: { size: 14 }
                            },
                            beginAtZero: true
                        }
                    },
                    animation: {
                        duration: 2000,
                        easing: 'easeInOutQuart'
                    }
                }
            });

            chartContainer.style.opacity = "1";
            chartInitialized = true;
        }
    }

    const observer = new IntersectionObserver(entries => {
        if (entries[0].isIntersecting) {
            initializeChart();
            observer.disconnect();
        }
    }, { threshold: 0.3 });

    observer.observe(chartContainer);
});
