<template>
    <div class="flex align-items-center lg:justify-content-between justify-content-end mb-2">
        <div class="col p-0 hidden lg:block">
            <div class="overview">
                <h2>Store detail</h2>
                <label>view more information of the store</label>
            </div>
        </div>
        <div class="col-fixed p-0">
            <CommonButton @click-event="onClickBack()" :label="'Back'" :class="'mr-2 p-button-outlined'"></CommonButton>
            <CommonButton v-if="viewMode" @click-event="onClickUpdate()" :label="'Update'"></CommonButton>
            <CommonButton v-else @click-event="onClickSave()" :label="'Save'"></CommonButton>
        </div>
    </div>
    <div class="grid w-full lg:flex-row flex-column lg:gap-0 gap-3 align-items-stretch justify-content-between"
        :disabled="screenMode === SCREEN_MODE.VIEW">
        <div class="w-12 pr-0 lg:pr-2 lg:w-6">
            <Card class="w-full p-2">
                <template #header>
                    <label class="card-header">{{ componentItems.get(section.STORE_INFORMATION)?.sectionName }}</label>
                </template>
                <template #content>
                    <InputTextWithLabel :disabled="viewMode"
                        v-for="item of componentItems.get(section.STORE_INFORMATION)?.items" class="py-1"
                        :label="item.label" :value="storeDetailRef![item.field]" v-bind:key="item.label">
                    </InputTextWithLabel>

                </template>
            </Card>
            <Card class="p-2 mt-3">
                <template #header>
                    <label class="card-header">
                        {{ componentItems.get(section.STORE_DESCRIPTION)?.sectionName }}
                    </label>
                </template>
                <template #content>
                    <TextareaWithLabel :disabled="viewMode" :label="item.label" :value="storeDetailRef![item.field]"
                        :rows="8" v-for="item of componentItems.get(section.STORE_DESCRIPTION)?.items"
                        v-bind:key="item.label">
                    </TextareaWithLabel>
                </template>
            </Card>
        </div>
        <div class="w-12 lg-0 lg:pl-2 lg:w-6">
            <div class="w-full flex flex-column">
                <Card class="w-full p-2 mb-3">
                    <template #header>
                        <label class="card-header">{{ componentItems.get(section.STORE_MANAGER)?.sectionName
                            }}</label>
                    </template>
                    <template #content>
                        <InputTextWithLabel :disabled="viewMode"
                            v-for="item of componentItems.get(section.STORE_MANAGER)?.items" :label="item.label"
                            v-model:value="storeDetailRef![item.field]" v-bind:key="item.label">
                        </InputTextWithLabel>

                    </template>
                </Card>
                <Card class="w-full p-2">
                    <template #header>
                        <label class="card-header">{{ componentItems.get(section.STORE_ACTIVITIES)?.sectionName
                            }}</label>
                    </template>
                    <template #content>
                        <div class="flex flex-column gap-2">
                            <InputTextWithLabel :disabled="viewMode"
                                v-for="item of componentItems.get(section.STORE_ACTIVITIES)?.items" :label="item.label"
                                v-model:value="storeDetailRef![item.field]" v-bind:key="item.label">
                            </InputTextWithLabel>
                        </div>

                    </template>
                </Card>
            </div>
        </div>
    </div>
    <CommonToast v-model:toast="toastRefDto"></CommonToast>
</template>
<script lang="ts">
import Card from 'primevue/card';
import { computed, defineComponent, onBeforeUnmount, onMounted, ref, type Ref } from 'vue';
import { useRoute } from 'vue-router';
import { get, put } from '@/utils/ApiUtils'
import type { ApiResponse } from '@/dto/ApiResponse';
import { ResponseStatusConstant } from '@/assets/Constants';
import router from '@/router';
import InputTextWithLabel from '@/components/module/InputTextWithLabel.vue';
import CommonToast from '@/components/module/CommonToast.vue';
import type { ToastRefDto } from '@/dto/ToastRefDto';
import TextareaWithLabel from '@/components/module/TextareaWithLabel.vue';
import CommonButton from '@/components/module/CommonButton.vue';

class StoreDetailResponse {
    id: string | undefined;
    name: string | undefined;
    contactNumber: string | undefined;
    emailAddress: string | undefined;
    address: string | undefined;
    description: string | undefined;
    openTime: string | undefined;
    closeTime: string | undefined;
    managerId: string | undefined;
    managerName: string | undefined;
    rate: string | undefined;
    liked: string | undefined;
    status: string | undefined;
}

export default defineComponent({
    name: "A120StoreAdd",
    components: {
        Card,
        CommonButton,
        TextareaWithLabel,
        InputTextWithLabel,
        CommonToast
    },
    setup: () => {
        const SCREEN_MODE = {
            VIEW: 0,
            UPDATE: 1
        }
        const screenMode = ref(SCREEN_MODE.VIEW);
        const viewMode = computed(() => screenMode.value === SCREEN_MODE.VIEW);

        const toastRefDto = ref<ToastRefDto | undefined>(undefined);

        const section = {
            STORE_INFORMATION: 0,
            STORE_DESCRIPTION: 2,
            STORE_MANAGER: 3,
            STORE_ACTIVITIES: 4,
        }

        const componentItems: Ref<Map<number, {
            'sectionName': string,
            items: { label: string, field: keyof StoreDetailResponse }[]
        }>> = ref(new Map());

        const setComponentItems = (storeDetail?: StoreDetailResponse) => {
            if (!storeDetail) {
                return;
            }
            componentItems.value.set(section.STORE_INFORMATION, {
                sectionName: 'Store informations',
                items: [
                    { label: 'Store name', field: 'name' },
                    { label: 'Phone number', field: 'contactNumber' },
                    { label: 'Email address', field: 'emailAddress' },
                    { label: 'Address', field: 'address' }
                ]
            });

            componentItems.value.set(section.STORE_DESCRIPTION, {
                sectionName: 'About us',
                items: [
                    { label: 'Description', field: 'description' }
                ]
            });

            componentItems.value.set(section.STORE_ACTIVITIES, {
                sectionName: 'Store\'s activities',
                items: [
                    { label: 'Open at', field: 'openTime' },
                    { label: 'Close at', field: 'closeTime' },
                    { label: 'Ratings', field: 'rate' },
                    { label: 'Total likes', field: 'liked' },
                    { label: 'Status', field: 'status' }
                ]
            });

            componentItems.value.set(section.STORE_MANAGER, {
                sectionName: 'Manager\'s information',
                items: [
                    { label: 'Manager', field: 'managerId' },
                    { label: 'Contact number', field: 'contactNumber' },
                ]
            });
        }

        const route = useRoute();
        const abortController = new AbortController();
        let storeDetailRef: Ref<StoreDetailResponse | undefined> = ref();

        const apiStoreDetail = async (id: number) => {
            get(`/vuejsdemo/store/detail/${id}`, abortController.signal)?.then(axiosResponse => {
                const response: ApiResponse = axiosResponse.data;
                if (response && response.status === ResponseStatusConstant.OK) {
                    storeDetailRef.value = response.item;
                    setComponentItems(storeDetailRef.value);
                }
            });
        }

        const init = async () => {
            const id = route.params.id;
            if (id) {
                await apiStoreDetail(Number(id));
            }
        }

        const onClickBack = () => {
            router.go(-1);
        }
        const onClickUpdate = () => {
            screenMode.value = SCREEN_MODE.UPDATE;
        }
        const onClickSave = async () => {
            put(`/vuejsdemo/store/update`, storeDetailRef.value, abortController.signal)?.then(axiosResponse => {
                const response: ApiResponse = axiosResponse.data;
                if (response && response.status === ResponseStatusConstant.OK) {
                    toastRefDto.value = {
                        severity: 'success',
                        message: 'Store\'s details updated successfully',
                        life: 3000
                    }
                } else {
                    toastRefDto.value = {
                        severity: 'error',
                        message: 'Store\'s details update failed',
                        life: 3000
                    }
                }
            }).catch((e) => {
                toastRefDto.value = {
                    severity: 'error',
                    message: 'Store\'s details update failed: ' + e,
                }
            });
        }

        onMounted(async () => {
            await init();
        });

        onBeforeUnmount(() => {
            abortController.abort();
        })

        return { SCREEN_MODE, screenMode, toastRefDto, viewMode, section, componentItems, storeDetailRef, onClickBack, onClickUpdate, onClickSave };
    }
})
</script>
<style lang="scss">
.card-header {
    color: #6EE7B7;
    font-size: 1.25rem;
    padding-left: 0.5rem;
}
</style>