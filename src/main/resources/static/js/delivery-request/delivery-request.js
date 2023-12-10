let deliveryApiServerHost = "http://localhost:30001";
async function displayDeliveryRequests(map, deliveryRequestModal) {
    const {InfoWindow} = await google.maps.importLibrary("maps");
    const {AdvancedMarkerElement, PinElement} = await google.maps.importLibrary(
        "marker",
    );
    let deliveryPartnerId = Cookies.get("deliveryPartnerId");
    let accessToken = Cookies.get("access-token");
    let deliveryRequestMaps = [];

    $.ajax({
        url: deliveryApiServerHost + "/apis/delivery-requests?deliveryPartnerId=" + deliveryPartnerId,
        type: "GET",
        statusCode: {
            401: function (response) {
                Cookies.remove("deliveryPartnerId");
                Cookies.remove("access-token");
                location.href = "/";
            },
            403: function (data) {
                Cookies.remove("deliveryPartnerId");
                Cookies.remove("access-token");
                location.href = "/";
            }
        },
        beforeSend: function (xhr) {
            console.log(">>> beforeSend: " + accessToken);
            xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
        },
        success: function (deliveryRequestData) {
            console.log(">>> success");
            console.log(deliveryRequestData);
            deliveryRequestData.deliveryRequests.forEach(function (item) {
                console.log(">>> Item: ", item);
                $.get(deliveryApiServerHost + "/apis/geocode/address?address=" + item.deliveryAddress, function (googleMapData, status) {
                    console.log(googleMapData);
                    let deliveryRequestMap = {};
                    deliveryRequestMap["title"] = item.deliveryAddress;
                    let lat = googleMapData.results[0].geometry.location.lat
                    let lng = googleMapData.results[0].geometry.location.lng
                    let latLng = new google.maps.LatLng(lat, lng);
                    deliveryRequestMap["position"] = new google.maps.LatLngAltitude(latLng);
                    deliveryRequestMaps.push(deliveryRequestMap);

                    // Create an info window to share between markers.
                    const infoWindow = new InfoWindow();

                    // Create the markers.
                    deliveryRequestMaps.forEach(({position, title}, i) => {
                        console.log(position, title);
                        const pin = new PinElement({
                            glyph: `${i + 1}`,
                        });
                        const marker = new AdvancedMarkerElement({
                            position,
                            map,
                            title: `${i + 1}. ${title}`,
                            content: pin.element,
                        });

                        // Add a click listener for each marker, and set up the info window.
                        marker.addEventListener("gmp-click", ({domEvent, latLng}) => {
                            console.log(">>> 지도 마커 클릭", domEvent, latLng);

                            infoWindow.close();
                            infoWindow.setContent(marker.title);
                            infoWindow.open(marker.map, marker);

                            $("#deliveryRequestModal").data("delivery-request-id", item.deliveryRequestId);
                            $("#deliveryRequestModal").data("order-id", item.orderId);
                            $("#deliveryRequestModal").data("order-shorten-id", item.orderShortenId);
                            deliveryRequestModal.show();
                        });
                    });
                });
            });
        }
    });
}

$(function () {
    let deliveryRequestModal = new bootstrap.Modal(document.getElementById('deliveryRequestModal'));
    let arrivalAtStoreDeliveryEventModal = new bootstrap.Modal(document.getElementById('arrivalAtStoreDeliveryEventModal'));
    let pickupCompleteModal = new bootstrap.Modal(document.getElementById('pickupCompleteModal'));
    let deliveryCompleteEventModal = new bootstrap.Modal(document.getElementById('deliveryCompleteEventModal'));
    let deliveryCompleteConfirmEventModal = new bootstrap.Modal(document.getElementById('deliveryCompleteConfirmEventModal'));

    let deliveryPartnerId = Cookies.get("deliveryPartnerId");
    let accessToken = Cookies.get("access-token");

    $(window).on('load', function() {
        createMap().then(map => displayDeliveryRequests(map, deliveryRequestModal));
    });

    // 배달 수락 버튼 클릭
    $(".delivery-request-accept-btn").on('click', function () {
        console.log(">>> 배달 요청 수락 클릭");
        let deliveryPartnerId = Cookies.get("deliveryPartnerId");
        let deliveryRequestId = $("#deliveryRequestModal").data("delivery-request-id");
        let requestData = {
            "deliveryRequestId": deliveryRequestId,
            "deliveryPartnerId": deliveryPartnerId,
            "deliveryRequestStatus": "ACCEPT"
        };

        $.ajax({
                url: deliveryApiServerHost + "/apis/delivery-requests/status",
                type: "PUT",
                data : JSON.stringify(requestData),
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                statusCode: {
                    401: function (response) {
                        Cookies.remove("deliveryPartnerId");
                        Cookies.remove("access-token");
                        location.href = "/";
                    },
                    403: function (data) {
                        Cookies.remove("deliveryPartnerId");
                        Cookies.remove("access-token");
                        location.href = "/";
                    }
                },
                beforeSend: function (xhr) {
                    console.log(">>> beforeSend: " + accessToken);
                    xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
                },
            })
        .done(function(data) {
            console.log(">>> 배달 요청 수락 성공: ", data);
            deliveryRequestModal.hide();
            arrivalAtStoreDeliveryEventModal.show();
        })
        .fail(function() {
            console.log(">>> 배달 요청 수락 처리시 에러");
        })
        .always(function() {
        });
    });

    // 배달 거절 버튼 클릭
    $(".delivery-request-reject-btn").on('click', function () {
        console.log(">>> 배달 요청 거절 클릭");
        let deliveryPartnerId = Cookies.get("deliveryPartnerId");
        let deliveryRequestId = $("#deliveryRequestModal").data("delivery-request-id");
        let requestData = {
            "deliveryRequestId": deliveryRequestId,
            "deliveryPartnerId": deliveryPartnerId,
            "deliveryRequestStatus": "REJECT"
        };

        $.ajax({
            url: deliveryApiServerHost + "/apis/delivery-requests/status",
            type: "PUT",
            data : JSON.stringify(requestData),
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            statusCode: {
                401: function (response) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                },
                403: function (data) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                }
            },
            beforeSend: function (xhr) {
                console.log(">>> beforeSend: " + accessToken);
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
        })
        .done(function(data) {
            console.log(">>> 배달 요청 거절 성공: ", data);
            deliveryRequestModal.hide();
        })
        .fail(function() {
            console.log(">>> 배달 요청 거절 처리시 에러");
        })
        .always(function() {
        });
    });

    // 매장 도착 클릭
    $(".arrival-at-store-btn").on('click', function () {
        console.log(">>> 매장 도착 클릭");
        let deliveryPartnerId = Cookies.get("deliveryPartnerId");
        let deliveryRequestId = $("#deliveryRequestModal").data("delivery-request-id");
        let requestData = {
            "deliveryRequestId": deliveryRequestId,
            "deliveryPartnerId": deliveryPartnerId,
            "deliveryStatus": "ARRIVAL_AT_STORE"
        };

        $.ajax({
            url: deliveryApiServerHost + "/apis/deliveries/status",
            type: "PUT",
            data : JSON.stringify(requestData),
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            statusCode: {
                401: function (response) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                },
                403: function (data) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                }
            },
            beforeSend: function (xhr) {
                console.log(">>> beforeSend: " + accessToken);
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
        })
            .done(function(data) {
                console.log(">>> 매장 도착 처리 성공: ", data);
                arrivalAtStoreDeliveryEventModal.hide();

                $(".pickup-dialog-order-shorten-id").text(data.orderShortenId);
                pickupCompleteModal.show();
            })
            .fail(function() {
                console.log(">>> 배달 요청 거절 처리시 에러");
            })
            .always(function() {
            });
    })

    // 픽업 완료 버튼 클릭
    $(".pickup-complete-btn").on('click', function () {
        console.log(">>> 픽업 완료 버튼 클릭");
        let deliveryPartnerId = Cookies.get("deliveryPartnerId");
        let deliveryRequestId = $("#deliveryRequestModal").data("delivery-request-id");
        let requestData = {
            "deliveryRequestId": deliveryRequestId,
            "deliveryPartnerId": deliveryPartnerId,
            "deliveryStatus": "PICKUP_COMPLETE"
        };

        $.ajax({
            url: deliveryApiServerHost + "/apis/deliveries/status",
            type: "PUT",
            data : JSON.stringify(requestData),
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            statusCode: {
                401: function (response) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                },
                403: function (data) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                }
            },
            beforeSend: function (xhr) {
                console.log(">>> beforeSend: " + accessToken);
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
        })
            .done(function(data) {
                console.log(">>> 픽업 완료 처리 성공: ", data);
                pickupCompleteModal.hide();

                $("#deliveryCompleteEventModal .order-shorten-id").text(data.orderShortenId);
                deliveryCompleteEventModal.show();
            })
            .fail(function() {
                console.log(">>> 배달 요청 거절 처리시 에러");
            })
            .always(function() {
            });
    })

    // 배달 완료 클릭
    $(".delivery-complete-btn").on('click', function () {
        console.log(">>> 배달 완료 클릭");
        let deliveryPartnerId = Cookies.get("deliveryPartnerId");
        let deliveryRequestId = $("#deliveryRequestModal").data("delivery-request-id");
        let requestData = {
            "deliveryRequestId": deliveryRequestId,
            "deliveryPartnerId": deliveryPartnerId,
            "deliveryStatus": "DELIVERY_COMPLETE"
        };

        $.ajax({
            url: deliveryApiServerHost + "/apis/deliveries/status",
            type: "PUT",
            data : JSON.stringify(requestData),
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            statusCode: {
                401: function (response) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                },
                403: function (data) {
                    Cookies.remove("deliveryPartnerId");
                    Cookies.remove("access-token");
                    location.href = "/";
                }
            },
            beforeSend: function (xhr) {
                console.log(">>> beforeSend: " + accessToken);
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            },
        })
        .done(function(data) {
            console.log(">>> 배달 완료 성공: ", data);
            deliveryCompleteEventModal.hide();

            $(".delivery-complete-modal-order-shorten-id").text(data.orderShortenId);
            $(".delivery-complete-delivery-fee").text(data.deliveryFee + "원");
            deliveryCompleteConfirmEventModal.show();
        })
        .fail(function() {
            console.log(">>> 배달 완료 처리시 에러");
        })
        .always(function() {
            location.reload();
        });
    })

    // 배달 내역 최종 확인 클릭
    $(".delivery-complete-confirm-btn").on('click', function () {
        deliveryCompleteConfirmEventModal.hide();
    });

    // 배달 계속 버튼 클릭
    $(".delivery-continue-btn").on('click', function () {
        deliveryCompleteConfirmEventModal.hide();
    });
});

async function createMap() {
    // Request needed libraries.
    const { Map } = await google.maps.importLibrary("maps");
    const map = new Map(document.getElementById("map"), {
        zoom: 12,
        center: { lat: 37.6515102, lng: 127.2427582 },
        mapId: "4504f8b37365c3d0",
    });
    return map
}