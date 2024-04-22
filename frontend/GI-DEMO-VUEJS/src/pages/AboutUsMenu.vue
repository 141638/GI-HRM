<template>
    <div class="main-wrapper">
        <div class="overview mb-4">
            <h2>About Us</h2>
            <label>about our story that may reach you out there</label>
        </div>
        <div>
            <img src="https://eluminoustechnologies.com/blog/wp-content/uploads/2023/09/blog-feature-image-7.jpg"
                class="mb-4 w-full">
            <p v-for="item of paragraphs" :key="item" class="py-1">
                {{ item }}
            </p>
        </div>
    </div>
</template>
<script setup lang="ts">

const paragraphs: string[] = [
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas eleifend, nunc eget egestas dignissim, metus enim gravida quam, et finibus eros eros vitae elit. Aliquam a augue laoreet, hendrerit erat vitae, placerat nisl. Aliquam eget gravida turpis. Praesent nunc ex, laoreet eget lacinia nec, malesuada et velit. Mauris sit amet tristique enim, quis dignissim elit. Suspendisse urna orci, ornare quis elementum nec, malesuada feugiat neque. Fusce et laoreet arcu. In at libero urna. Nunc ipsum nunc, ornare vitae vehicula at, tincidunt sit amet metus. Sed elementum iaculis tempus. Nulla fermentum tellus semper, sodales lectus at, vestibulum leo. Praesent mollis neque id ex aliquet, eu ullamcorper nisl vestibulum. Integer mattis convallis orci, in dictum nulla.",
    "Donec tempor scelerisque ultricies. Duis interdum porttitor lectus, in tempus justo rutrum vitae. Vivamus eget semper orci, id posuere magna. Nullam elementum massa eu lorem mattis commodo. Etiam ut pulvinar risus. Nunc ac dapibus neque, eget malesuada augue. Nullam quis diam sem. Phasellus et commodo magna. Duis enim ipsum, congue vitae congue sed, hendrerit in ex.",
    "Integer fermentum, tellus vitae elementum mollis, orci ante tempus lorem, sed eleifend ipsum est non nunc. Suspendisse varius nisi in accumsan porttitor. Curabitur feugiat efficitur quam eu pellentesque. Phasellus cursus libero id tortor molestie ornare. Aenean ultrices semper porttitor. Donec id nibh non metus lobortis lacinia. Sed consequat, nibh imperdiet efficitur rhoncus, neque tellus tristique leo, a facilisis quam augue ac odio. Nullam in laoreet odio. Integer lacinia lobortis gravida.",
    "Morbi egestas leo vitae mauris placerat efficitur. Quisque bibendum nunc nec leo tristique mattis. Nullam lobortis convallis mauris et venenatis. Aenean dignissim mauris ac ante vehicula, eu sollicitudin massa vehicula. Curabitur blandit libero at ligula auctor tempor condimentum vitae ante. Duis vehicula consectetur lectus, vitae imperdiet mi placerat ac. Pellentesque ac dolor nec felis aliquam volutpat. Integer convallis enim ex. Duis elementum rhoncus eros. Nunc vel sapien a dui dignissim imperdiet vitae eu nulla. Integer blandit fringilla tincidunt.",
    "Sed porta placerat nunc, non imperdiet nunc fringilla eu. Etiam eget velit lacus. Nulla justo ante, porttitor sit amet leo porttitor, sodales fringilla purus. Maecenas placerat consequat eros in consectetur. Fusce sagittis augue vitae gravida convallis. Quisque nunc tellus, consectetur vel volutpat ac, commodo vitae mi. Suspendisse in felis nec risus consequat dapibus nec ac tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
];
</script>
<style scoped lang="scss">
.main-wrapper {
    max-width: 50vw;
}

.overview {
    color: white;
}
</style>
