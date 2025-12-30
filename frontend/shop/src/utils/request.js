import axios from "axios"

const service = axios.create({
    baseURL: "/api",
    timeout: 5000, // 设置超时时间
})

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 将token携带到请求头中
        const token = localStorage.getItem("token")
        if (token) {
            config.headers["Authorization"] = `Bearer ${token}`
        }
        return config // 必须要返回的
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        if (response.status) {
            return response.data
        } else {
            return Promise.reject(new Error("请求失败"))
        }
    },
    (error) => {
        return Promise.reject(error)
    }
)

export default service
