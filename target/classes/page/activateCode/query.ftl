<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-12">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/activateCode/query" method="post">
                        <div class="input-group margin">
                            <div class="form-group">
                                <label>批次名称</label>
                                <input type="text" class="form-control" name="groupName" placeholder="批次名称"
                                        value="${RequestParameters["groupName"]}"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="form-control btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">查询
                                </button>
                            </div>
                        </div>
                </div>
                </form>
                <!-- /input-group -->
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">批次列表</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>code</th>
                                <th>批次名称</th>
                                <th>是否已经使用</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list data as item>
                                <tr>
                                    <td>${item.code}</td>
                                    <td>${item.groupName}</td>
                                    <td>
                                        <#if item.used==0>
                                            未使用
                                        <#else >
                                            使用
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- /.box-body -->
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
