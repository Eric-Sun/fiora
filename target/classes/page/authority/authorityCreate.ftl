<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/authority/authorityCreate" method="post">
                        <div class="input-group margin">

                            <div class="form-group form-inline">
                                <label>角色名称</label>
                                <input type="text" class="form-control" name="name"/>
                            </div>
                            <div class="form-group">
                                <label>配置相关资源</label>
                                <#list resourceList as item >
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="resourceIdList" value="${item.id}"/>
                                        ${item.name}
                                        </label>
                                    </div>
                                </#list>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">创建新账户
                                </button>
                            </div>
                        </div>
                </div>
                </form>
                <!-- /input-group -->
            </div>
        </div>
        </div>
    </section>
</aside>
</@layout.mainLayout>
<script type="text/javascript">
    $(function () {
                $("#begin").datepicker().datepicker("option", {
                    "dateFormat": "yy-mm-dd"
                });
                ;
            }
    );
</script>
