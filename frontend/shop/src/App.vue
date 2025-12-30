<!-- App.vue -->

<template>
    <div>
        <router-view :key="$route.fullPath" />
        <tar-bar v-if="showTabBar"></tar-bar>
    </div>
</template>

<script>
import { ref, watch } from "vue"
import { useRoute } from "vue-router"
import TarBar from "./components/TarBar/index.vue"

export default {
    components: {
        TarBar,
    },
    setup() {
        const route = useRoute()
        const showTabBar = ref(false)

        watch(
            () => route.meta.showTabBar,
            (newShowTabBar) => {
                showTabBar.value = newShowTabBar
            }
        )

        return {
            showTabBar,
        }
    },
}
</script>
