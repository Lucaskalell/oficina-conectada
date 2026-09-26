import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_event.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_service.dart';
import 'package:oficina_conectada_front/modules/auth/recuperar_senha/recuperar_senha_state.dart';

class RecuperarSenhaBloc extends Bloc<RecuperarSenhaEvent, RecuperarSenhaState> {
  final RecuperarSenhaService _recuperarSenhaService;

  RecuperarSenhaBloc(this._recuperarSenhaService) : super(RecuperarSenhaInicial()) {
    on<SolicitarToken>(_onSolicitarToken);
    on<RedefinirSenha>(_onRedefinirSenha);
  }

  Future<void> _onSolicitarToken(
    SolicitarToken evento,
    Emitter<RecuperarSenhaState> emit,
  ) async {
    emit(RecuperarSenhaCarregando());
    try {
      await _recuperarSenhaService.solicitarToken(evento.email);
      emit(TokenEnviado());
    } catch (e) {
      emit(RecuperarSenhaErro(e.toString().replaceFirst('Exception: ', '')));
    }
  }

  Future<void> _onRedefinirSenha(
    RedefinirSenha evento,
    Emitter<RecuperarSenhaState> emit,
  ) async {
    emit(RecuperarSenhaCarregando());
    try {
      await _recuperarSenhaService.redefinirSenha(evento.token, evento.novaSenha);
      emit(SenhaRedefinida());
    } catch (e) {
      emit(RecuperarSenhaErro(e.toString().replaceFirst('Exception: ', '')));
    }
  }
}
