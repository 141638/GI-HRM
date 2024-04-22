<template>
    <div class="flex flex-column gap-2">
        <label :for="inputId">{{ label }}</label>
        <Textarea :disabled="disabled" :id="inputId" v-model="value" :rows="rows" class="w-full"
            style="resize: none;"></Textarea>
    </div>
</template>
<script setup lang="ts">
import Textarea from 'primevue/textarea';
import { computed } from 'vue';

const disabled = defineModel<boolean>('disabled', { default: false });
const rows = defineModel<number>('rows', { default: 5 });
const label = defineModel<string>('label', { required: true });
const value = defineModel<string>('value');
const inputId = computed(() => label.value.replace(/\\s/g, '-').toLowerCase() + '-id');
</script>