import store from '../store'
Vue.directive('has', {
  inserted: function (el, binding) {
    if (!permissionJudge(binding.value)) {
      el.parentNode.removeChild(el);
    }

    function permissionJudge(value) {
      // 此处store.getters.getMenuBtnList代表vuex中储存的按钮菜单数据
      let list = store.getters.btnPermissions;
      for (let item of list) {
        if (item === value) {
          return true;
        }
      }
      return false;
    }
  }
});

