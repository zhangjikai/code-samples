## 配置directive
```html
app.directive("jqueryUpload", ["fileNavigator", function (fileNavigator) {
        return {
            require: '?ngModel',
            restrict: 'A',
            link: function ($scope, element, attrs, ngModel) {
                extraObj = $(element).uploadFile({
                    url: "uploadFile",
                    fileName: "file",
                    showFileSize: true,
                    showDelete: true,
                    autoSubmit: false,
                    statusBarWidth: "auto",
                    dragdropWidth: "auto",
                    dragdropHeight: 200,
                    uploadStr: "选择",
                    cancelStr: "取消",
                    abortStr: "终止",
                    deleteStr: "删除",
                    dynamicFormData: function () {
                        var data = {"path": $scope.fileNavigator.currentPath.join('/')};
                        return data;
                    },
                    onSuccess: function (files, data, xhr, pd) {
                        var obj = eval(data);

                        if (obj.success) {
                            $scope.fileNavigator.refresh();
                        } else {
                            pd.progressDiv.hide();
                            pd.statusbar.append("<span class='ajax-file-upload-error'>ERROR: " + obj.error + "</span>");
                        }
                    }
                });
            }
        };
    }]);
```

## 使用directive
```html
<div id="fileuploader" jquery-upload="" >选择</div>
```