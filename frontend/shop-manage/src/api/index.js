import request from "../utils/request.js"

export function getLogin(data) {
    return request({
        url: "/login",
        method: "POST",
        data,
    })
}

export function getUser() {
    return request({
        url: "/alluser",
        method: "get",
    })
}

export function searchUser(username) {
    return request({
        url: "/searchuser",
        method: "get",
        params: { username }
    })
}
export function stopUser(id) {
    return request({
        url: `/stopuser/${id}`,
        method: "get",
    })
}
export function passUser(id) {
    return request({
        url: `/passuser/${id}`,
        method: "get",
    })
}
export function updateinfo(data) {
    return request({
        url: "/updatePersonalInfo",
        method: "put",
        data,
    })
}

export function getShop() {
    return request({
        url: "/list/all",
        method: "get",
    })
}
export function offList(id) {
    return request({
        url: `/list/offshelf/${id}`,
        method: "get",
    })
}
export function passList(id) {
    return request({
        url: `/list/pass/${id}`,
        method: "get",
    })
}
export function addList(data) {
    return request({
        url: "/list/add",
        method: "POST",
        data,
    })
}
export function deletelist(id) {
    return request({
        url: `/list/delete/${id}`,
        method: "delete",
    })
}

export function updateshop(id, data) {
    return request({
        url: `/list/update/${id}`,
        method: "put",
        data,
    })
}
export function getOrder() {
    return request({
        url: "/order/allorder",
        method: "get",
    })
}
export function updateorder(data) {
    return request({
        url: "/order/update/",
        method: "put",
        data,
    })
}
export function deleteorder(id) {
    return request({
        url: `/order/delete/${id}`,
        method: "delete",
    })
}

// ========== 新增功能 API ==========

// 评价管理
export function getAllReviews() {
    return request({
        url: "/review/admin/all",
        method: "get",
    })
}

export function deleteReview(id) {
    return request({
        url: `/review/delete/${id}`,
        method: "delete",
    })
}



// 举报管理
export function getAllReports() {
    return request({
        url: "/report/admin/all",
        method: "get",
    })
}

export function getReportsByStatus(status) {
    return request({
        url: `/report/admin/status/${status}`,
        method: "get",
    })
}

export function processReport(id, status, adminRemark, processedBy) {
    return request({
        url: `/report/admin/process/${id}`,
        method: "PUT",
        params: { 
            status: parseInt(status), 
            adminRemark, 
            processedBy 
        },
    })
}

export function updateReportStatus(id, status) {
    return request({
        url: `/report/admin/status/${id}`,
        method: "PUT",
        params: { status },
    })
}

export function deleteReport(id) {
    return request({
        url: `/report/admin/delete/${id}`,
        method: "delete",
    })
}

export function getPendingReportCount() {
    return request({
        url: "/report/admin/pendingCount",
        method: "get",
    })
}

// 消息管理
export function getAllMessages() {
    return request({
        url: "/message/admin/all",
        method: "get",
    })
}

export function deleteMessage(id) {
    return request({
        url: `/message/delete/${id}`,
        method: "delete",
    })
}

// 收藏统计
export function getFavoriteStats() {
    return request({
        url: "/favorite/admin/stats",
        method: "get",
    })
}
