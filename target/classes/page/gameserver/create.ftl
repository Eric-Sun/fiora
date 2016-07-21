<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/gameserver/create" method="post">
                        <div class="input-group margin">
                            <div class="form-group form-inline">
                                <label>服务器名称</label>
                                <input type="text" class="form-control" name="name" placeholder="请输入新服务器名称"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>服务器ip地址</label>
                                <input type="text" class="form-control" name="ip" placeholder="请输入服务器ip"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>服务器端口</label>
                                <input type="text" class="form-control" name="port" placeholder="请输入服务器端口"/>
                            </div>

                            <div class="form-group">
                                <label>开服时间</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input id="begin" type="text" name="opentime" class="form-control"/>
                                </div>
                                <!-- /.input group -->
                            </div>
                            <div class="form-group">
                                <label>服务器状态</label>
                                <select class="form-control" name="status">
                                    <option value="0">等待开服</option>
                                    <option value="1">正常运行中</option>
                                    <option value="2">维护中</option>
                                    <option value="-1">停服</option>
                                </select>
                            </div>
                            <!-- /.input group -->

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">创建新的服务器
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
