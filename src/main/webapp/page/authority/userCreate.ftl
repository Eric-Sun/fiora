<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/authority/userCreate" method="post">
                        <div class="input-group margin">

                            <div class="form-group form-inline">
                                <label>用户名称</label>
                                <input type="text" class="form-control" name="name" value="${user.name}"/>
                            </div>
                            <div class="form-group">
                                <label>角色</label>
                                <select class="form-control" name="authorityId">
                                    <#list authorityList as item>
                                        <option value="${item.id}"
                                                >${item.name}
                                        </option>
                                    </#list>


                                </select>
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
