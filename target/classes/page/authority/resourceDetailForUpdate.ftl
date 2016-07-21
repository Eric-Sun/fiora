<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/authority/resourceUpdate" method="post">
                        <div class="input-group margin">

                            <div class="form-group form-inline">
                                <label>资源id</label>
                                <input type="text" class="form-control" name="id" value="${resource.id}"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>资源名称</label>
                                <input type="text" class="form-control" name="name" value="${resource.name}"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">更新资源
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
