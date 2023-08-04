<template>
  <div class="frame">
    <div class="image-frame">
        <img src="../assets/popping_404.png" alt="">
    </div>
		<div class="error-text font-kor">
			해당 페이지는 존재하지 않는 페이지입니다.<br>다시 확인해주세요.
		</div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.frame {
  position: relative;
  width: 100%;
  height: calc(var(--vh, 1vh) * 100);
  overflow: hidden;
	display: flex;
	flex-direction: column;
}

.image-frame {
	position: relative;
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	top: 35%;
}

.error-text {
	position: relative;
	width: 100%;
	display: flex;
	justify-content: center;
	text-align: center;
	top: 40%;
	font-size: 85%;
}
</style>