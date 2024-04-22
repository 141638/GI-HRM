<template>
    <div class="overview">
        <h2>Create new store</h2>
        <label>create a new store</label>
    </div>
    <Card>

    </Card>
    <Button label="Submit" @click="$router.go(-1)"></Button>
</template>
<script lang="ts">
import Button from 'primevue/button';
import Card from 'primevue/card';
import { defineComponent } from 'vue';

export default defineComponent({
    name: "A120StoreAdd",
    components: {
        Card,
        Button
    },
    setup: () => {
        const init = () => { }
        return { init };
    }
})
</script>
<style lang="scss"></style>