<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/activateCode/create" method="post">
                        <div class="input-group margin">
                            <div class="form-group form-inline">
                                <label>批次名称</label>
                                <input type="text" class="form-control" name="groupName" placeholder="批次名称"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>数量</label>
                                <input type="text" class="form-control" name="count" placeholder="数量"/>
                            </div>

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">开始生成
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
