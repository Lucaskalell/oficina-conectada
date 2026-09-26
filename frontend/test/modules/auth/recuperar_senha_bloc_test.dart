import 'package:flutter_test/flutter_test.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_bloc.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_event.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_service.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_state.dart';

class RecuperarSenhaServiceFalso implements RecuperarSenhaService {
  Exception? erroAoSolicitar;
  Exception? erroAoRedefinir;
  String? tokenRecebido;

  @override
  Future<void> solicitarToken(String email) async {
    if (erroAoSolicitar != null) throw erroAoSolicitar!;
  }

  @override
  Future<void> redefinirSenha(String token, String novaSenha) async {
    tokenRecebido = token;
    if (erroAoRedefinir != null) throw erroAoRedefinir!;
  }
}

void main() {
  late RecuperarSenhaServiceFalso service;
  late RecuperarSenhaBloc bloc;

  setUp(() {
    service = RecuperarSenhaServiceFalso();
    bloc = RecuperarSenhaBloc(service);
  });

  tearDown(() => bloc.close());

  test('solicitar código avança para a etapa de redefinição sem expor token', () async {
    bloc.add(const SolicitarToken('mecanico@oficina.com'));

    await expectLater(
      bloc.stream,
      emitsInOrder([isA<RecuperarSenhaCarregando>(), isA<TokenEnviado>()]),
    );
  });

  test('redefinir senha envia o código digitado pelo usuário', () async {
    bloc.add(const RedefinirSenha(token: 'codigo-do-email', novaSenha: 'novaSenha123'));

    await expectLater(
      bloc.stream,
      emitsInOrder([isA<RecuperarSenhaCarregando>(), isA<SenhaRedefinida>()]),
    );
    expect(service.tokenRecebido, 'codigo-do-email');
  });

  test('mostra a mensagem do backend sem o prefixo Exception', () async {
    service.erroAoRedefinir = Exception('Código expirado');

    bloc.add(const RedefinirSenha(token: 'antigo', novaSenha: 'novaSenha123'));

    await expectLater(
      bloc.stream,
      emitsInOrder([
        isA<RecuperarSenhaCarregando>(),
        isA<RecuperarSenhaErro>().having((e) => e.mensagem, 'mensagem', 'Código expirado'),
      ]),
    );
  });
}
