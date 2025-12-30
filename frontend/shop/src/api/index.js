import request from "@/utils/request"

export function getRegister(data) {
    return request({
        url: "/register",
        method: "POST",
        data,
    })
}
export function getLogin(data) {
    return request({
        url: "/login",
        method: "POST",
        data,
    })
}
export function getUpload(data) {
    return request({
        url: "/upload",
        method: "POST",
        data,
    })
}
export function addList(data) {
    return request({
        url: "/list/add",
        method: "POST",
        data,
    })
}
export function getList() {
    return request({
        url: "/list/all",
        method: "get",
    })
}
export function passList(id) {
    return request({
        url: `/list/pass/${id}`,
        method: "get",
    })
}
export function deleteList(id) {
    return request({
        url: `/list/delete/${id}`,
        method: "delete",
    })
}
export function getType(type) {
    return request({
        url: `/list/type/${type}`,
        method: "get",
    })
}
export function getMyPush(user) {
    return request({
        url: `/list/mypush/${user}`,
        method: "get",
    })
}
export function getMySelled(user) {
    return request({
        url: `/list/myselled/${user}`,
        method: "get",
    })
}
export function getDetail(id) {
    return request({
        url: `/list/detail/${id}`,
        method: "get",
    })
}
export function addOrder(data) {
    return request({
        url: "/order/add",
        method: "POST",
        data,
    })
}
export function getPersonOrder(username) {
    return request({
        url: `/order/personal/${username}`,
        method: "get",
    })
}
export function getBusinessOrder(username) {
    return request({
        url: `/order/business/${username}`,
        method: "get",
    })
}

export function getUpdateInfo(data) {
    return request({
        url: `/updatePersonalInfo`,
        method: "put",
        data,
    })
}
export function getSearch(keyword) {
    return request({
        url: `/list/search/${keyword}`,
        method: "get",
    })
}
export function getDeleteOrder(id) {
    return request({
        url: `/order/delete/${id}`,
        method: "delete",
    })
}
export function getAllList() {
    return request({
        url: "/list/all",
        method: "get",
    })
}

// ========== 新增功能 API ==========

// 评价相关
export function addReview(data) {
    return request({
        url: "/review/add",
        method: "POST",
        data,
    })
}

export function getReviewsByBusinessId(businessId) {
    return request({
        url: `/review/business/${businessId}`,
        method: "get",
    })
}

export function getReviewsByUsername(username) {
    return request({
        url: `/review/user/${username}`,
        method: "get",
    })
}

export function getUserRating(username) {
    return request({
        url: `/review/rating/${username}`,
        method: "get",
    })
}

// 收藏相关
export function addFavorite(data) {
    return request({
        url: "/favorite/add",
        method: "POST",
        data,
    })
}

export function removeFavorite(username, businessId) {
    return request({
        url: "/favorite/remove",
        method: "DELETE",
        params: { username, businessId },
    })
}

export function getFavoritesByUsername(username) {
    return request({
        url: `/favorite/list/${username}`,
        method: "get",
    })
}

export function checkFavorite(username, businessId) {
    return request({
        url: "/favorite/check",
        method: "get",
        params: { username, businessId },
    })
}

export function getFavoriteFolders(username) {
    return request({
        url: `/favorite/folders/${username}`,
        method: "get",
    })
}

export function updateFavoriteFolder(username, oldFolderName, newFolderName) {
    return request({
        url: "/favorite/folder/rename",
        method: "PUT",
        params: { username, oldFolderName, newFolderName },
    })
}

// 消息相关
export function sendMessage(data) {
    return request({
        url: "/message/send",
        method: "POST",
        data,
    })
}

export function getConversation(user1, user2) {
    return request({
        url: "/message/conversation",
        method: "get",
        params: { user1, user2 },
    })
}

export function getChatList(username) {
    return request({
        url: `/message/chatList/${username}`,
        method: "get",
    })
}

export function getUnreadCount(username) {
    return request({
        url: `/message/unreadCount/${username}`,
        method: "get",
    })
}

export function markMessageAsRead(messageId) {
    return request({
        url: `/message/read/${messageId}`,
        method: "PUT",
    })
}

export function markAllAsRead(username) {
    return request({
        url: `/message/readAll/${username}`,
        method: "PUT",
    })
}

export function getSystemMessages(username) {
    return request({
        url: `/message/system/${username}`,
        method: "get",
    })
}



// 举报相关
export function addReport(data) {
    return request({
        url: "/report/add",
        method: "POST",
        data,
    })
}

export function getMyReports(username) {
    return request({
        url: `/report/my/${username}`,
        method: "get",
    })
}

export function getReportedByMe(username) {
    return request({
        url: `/report/reported/${username}`,
        method: "get",
    })
}
