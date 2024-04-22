<template>
    <Button :label="label" :icon="icon" :class='customClass' @click="$emit('click-event')" raised></Button>
</template>
<script setup lang="ts">
import Button from 'primevue/button';

const label = defineModel<string>('label');
const customClass = defineModel<string>('class')
const icon = defineModel<string>('icon');
</script>